package io.teamcity.plugins.carbonetes;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.BuildRunnerContext;

/**
 * @author carbonetes
 *
 */
public class CarbonetesAPI extends AbstractAPIWorker {
	
	// Private Fields
	private String token;
	private String policyResult;
	private String finalAction;
	private String innerResponse;
	// End Private Fields
	
	/**
	 * CarbonetesAPI
	 * 
	 * @param configuration
	 * @param runnerContext
	 */
	public CarbonetesAPI(Configuration configuration, BuildRunnerContext runnerContext) {
		this.configuration	= configuration;
		this.runnerContext = runnerContext;
	}

	public void initialize() throws RunBuildException {
		
		runnerContext.getBuild().getBuildLogger().message("========Initializing Plugin========");
		this.initializeAPICall();
		
		
		if (isAuthenticationGranted()) {
			runnerContext.getBuild().getBuildLogger().message("Authentication Success!");
			
		}else {
			runnerContext.getBuild().getBuildLogger().message("Authentication Failed!");
			if (configuration.isFailBuildOnCriticalPluginError()) {
				throw new RunBuildException(RunnerConstants.ERROR_MESSAGE);
			}
		}
		
	}
	
	/**
	 * Authentication Check
	 * 
	 * @throws RunBuildException
	 */
	@SuppressWarnings("unchecked")
	private Boolean isAuthenticationGranted() throws RunBuildException {
		
		String url = RunnerConstants.CARBONETES_ANALYSIS_CHECKER;
		runnerContext.getBuild().getBuildLogger().message("Authenticating...");
		httpPost = new HttpPost(url);

		try {
			String username = configuration.getUsername();
			String password = configuration.getPassword();
			String registryUri = configuration.getRegistryUri();
			String image = configuration.getImage();
			
			JSONObject	jsonBody	= new JSONObject();
			
			jsonBody.put(RunnerConstants.REGISTRY_URI, registryUri);
			jsonBody.put(RunnerConstants.REPO_IMAGE_TAG, image);
			jsonBody.put(RunnerConstants.USERNAME, username);
			jsonBody.put(RunnerConstants.PASSWORD, password);
			
			String			body	= jsonBody.toString();
			StringEntity	content	= new StringEntity(body);

			content.setContentType("application/json");
			httpPost.setEntity(content);

			response		= httpclient.execute(httpPost, context);
			statusCode		= response.getStatusLine().getStatusCode();
			responseBody	= EntityUtils.toString(response.getEntity());
			
			JsonNode		jsonNode	= null;
			ObjectMapper	mapper		= new ObjectMapper();

			jsonNode			= mapper.readTree(responseBody);
			token			  	= jsonNode.findPath("token").asText();
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RunBuildException(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new RunBuildException(e.getMessage());
		}

		return null != token;
  }
	
	
	/**
	 * Performs Comprehensive Analysis
	 * 
	 * @throws RunBuildException
	 */
	public void performComprehensiveAnalysis() throws RunBuildException {
	  	String url = RunnerConstants.CARBONETES_ANALYSIS_STATUS;
	  	runnerContext.getBuild().getBuildLogger().message("=======Comprehensive Analysis======");
	  	runnerContext.getBuild().getBuildLogger().message("Scanning Image...");
		httpPost = new HttpPost(url);
		
		try {
			JSONParser parser 					= new JSONParser();
			JSONObject json 					= (JSONObject) parser.parse(responseBody);
			String			body				= json.toString();
			StringEntity content;
			int retry							= 1;
			content 							= new StringEntity(body);
			content.setContentType("application/json");
			httpPost.setEntity(content);
			
			JsonNode		jsonNode			= null;
			ObjectMapper	mappers				= new ObjectMapper();
			jsonNode							= mappers.readTree("false");
			
			while (jsonNode.asText() != "true") {
				if (System.currentTimeMillis() >= RunnerConstants.MILLISECONDS) {
					response					= httpclient.execute(httpPost, context);
					statusCode					= response.getStatusLine().getStatusCode();
					innerResponse				= EntityUtils.toString(response.getEntity());
					
					ObjectMapper	mapper		= new ObjectMapper();
	
					jsonNode					= mapper.readTree(innerResponse);
					
					retry++;
				}
				
				if (retry > RunnerConstants.ENGINE_TIMEOUT) {
					break;
				}
			}
			if (jsonNode.asText() == "true") {
				runnerContext.getBuild().getBuildLogger().message("Scanning Image Complete!");
			}else {
				runnerContext.getBuild().getBuildLogger().message("Analysis Failed");
				if (configuration.isFailBuildOnCriticalPluginError()) {
					throw new RunBuildException(RunnerConstants.ERROR_MESSAGE);
				}
			}
		} catch (IOException e) {
			
			e.printStackTrace();
			if (configuration.isFailBuildOnCriticalPluginError()) {
				throw new RunBuildException(RunnerConstants.ERROR_MESSAGE);
			}
		} catch (ParseException e) {
			
			e.printStackTrace();
			if (configuration.isFailBuildOnCriticalPluginError()) {
				throw new RunBuildException(RunnerConstants.ERROR_MESSAGE);
			}
		}
	}
	
	/**
	 * Get Analysis Result
	 * 
	 * @throws RunBuildException
	 */
	public void getAnalysisResult() throws RunBuildException {
	  	String url 						= RunnerConstants.CARBONETES_ANALYSIS_RESULT;
	  	
		httpPost 						= new HttpPost(url);
		
		jsonBody						= new JSONObject();
		httpclient						= HttpClientBuilder.create().build();
		context							= HttpClientContext.create();
		
		runnerContext.getBuild().getBuildLogger().message("Generating Reports...");
		
		try {
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(responseBody);
			String			body	= json.toString();
			StringEntity content;	
			
			content = new StringEntity(body);
			content.setContentType("application/json");
			
			httpPost.setEntity(content);
			response					= httpclient.execute(httpPost, context);
			statusCode					= response.getStatusLine().getStatusCode();
			responseBody				= EntityUtils.toString(response.getEntity());
			
			ObjectMapper	mapper		= new ObjectMapper();
			JsonNode		jsonNode	= null;
			
			jsonNode					= mapper.readTree(responseBody);
			
			runnerContext.getBuild().getBuildLogger().message("Generating Reports Complete!");
			runnerContext.getBuild().getBuildLogger().message("===========Analysis Result=========");
			
			if (statusCode == HttpStatus.SC_OK) {
				
				policyResult 			= jsonNode.findPath(RunnerConstants.REPO_IMAGE_ENVIRONMENTS)
						.findPath(RunnerConstants.POLICY_EVALUATION_LATEST).findPath("policyResult").asText();
				finalAction 			= jsonNode.findPath(RunnerConstants.REPO_IMAGE_ENVIRONMENTS)
				        .findPath(RunnerConstants.POLICY_EVALUATION_LATEST).findPath("finalAction").asText();
				 
				runnerContext.getBuild().getBuildLogger().message("Policy Result : " + policyResult);
				runnerContext.getBuild().getBuildLogger().message("Final Action : " + finalAction);
					
				String workingDirectoryPath = runnerContext.getWorkingDirectory().getAbsolutePath();
				String jsonContent 			= jsonNode.toString();
				String path 				= workingDirectoryPath + "/artifacts.json";

				Files.write( Paths.get(path), jsonContent.getBytes(StandardCharsets.UTF_8));
				 
				if (configuration.isFailBuildOnPolicyEvaluationFinalResult() && policyResult.equalsIgnoreCase("failed")) {
					throw new RunBuildException(RunnerConstants.ERROR_ON_FAIL_POLICY_EVALUATION);
				} else {	
					if (policyResult.toString().equalsIgnoreCase("failed")) {
							runnerContext.getBuild().getBuildLogger().message(RunnerConstants.POLICY_EVALUATION_IGNORED);
					}
				}
				 
			}else {
				if (configuration.isFailBuildOnCriticalPluginError()) {
					throw new RunBuildException(RunnerConstants.ERROR_MESSAGE);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			if (configuration.isFailBuildOnCriticalPluginError()) {
				throw new RunBuildException(RunnerConstants.ERROR_MESSAGE + "\n" + e.getMessage());
			}
		} catch (ParseException e) {
			e.printStackTrace();
			if (configuration.isFailBuildOnCriticalPluginError()) {
				throw new RunBuildException(RunnerConstants.ERROR_MESSAGE + "\n" + e.getMessage());
			}
		}
	}
}	
