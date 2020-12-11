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
import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.BuildRunnerContext;
import jetbrains.buildServer.agent.artifacts.ArtifactsWatcher;

/**
 * @author carbonetes
 *
 */
public class CarbonetesAPI extends AbstractAPIWorker {
	
	// Private Fields
	private String policyResult;
	private String finalAction;
	// End Private Fields
	
	public CarbonetesAPI(Configuration configuration, BuildRunnerContext runnerContext,
			ArtifactsWatcher artifactsWatcher) {
		super(configuration,runnerContext,artifactsWatcher);
		
		this.configuration		= configuration;
		this.runnerContext 		= runnerContext;
		this.artifactsWatcher 	= artifactsWatcher;
	}

	/**
	 * CarbonetesAPI
	 * 
	 * @param configuration
	 * @param runnerContext
	 */

	public void initialize() throws RunBuildException {
		this.initializeAPICall();
	}
	
	/**
	 * Performs Comprehensive Analysis
	 * 
	 * @throws RunBuildException
	 */
	@SuppressWarnings("unchecked")
	public void performComprehensiveAnalysis() throws RunBuildException {
		
		runnerContext.getBuild().getBuildLogger().message("===========Scan details===========");
		runnerContext.getBuild().getBuildLogger().message("Image : " + configuration.getImage());
		runnerContext.getBuild().getBuildLogger().message("Registry URI : " + configuration.getRegistryUri());
		runnerContext.getBuild().getBuildLogger().message("Policy bundle ID : " + configuration.getPolicyBundleID());
		runnerContext.getBuild().getBuildLogger().message("Carbonetes Engine Timeout : " + configuration.getEngineTimeout());
		runnerContext.getBuild().getBuildLogger().message("Fail build on policy evaluation result : " + configuration.isFailBuildOnPolicyEvaluationFinalResult());
		runnerContext.getBuild().getBuildLogger().message("Fail build on critical plugin error : " + configuration.isFailBuildOnCriticalPluginError());
		runnerContext.getBuild().getBuildLogger().message("Performing Comprehensive Analysis...");
		String url = RunnerConstants.CARBONETES_ANALYSIS_CHECKER;
		httpPost = new HttpPost(url);

		try {
			String username 			= configuration.getUsername();
			String password 			= configuration.getPassword();
			String registryUri			= configuration.getRegistryUri();
			String image 				= configuration.getImage();
			String policyBundleId		= configuration.getPolicyBundleID();
			int engineTimeOut			= configuration.getEngineTimeout();
			
			JSONObject	jsonBody		= new JSONObject();
			
			jsonBody.put(RunnerConstants.REGISTRY_URI, registryUri);
			jsonBody.put(RunnerConstants.REPO_IMAGE_TAG, image);
			jsonBody.put(RunnerConstants.USERNAME, username);
			jsonBody.put(RunnerConstants.PASSWORD, password);
			jsonBody.put(RunnerConstants.POLICY_BUNDLE_UUID, policyBundleId);
			jsonBody.put(RunnerConstants.TIMEOUT, engineTimeOut);
			
			String			body		= jsonBody.toString();
			StringEntity	content		= new StringEntity(body);

			content.setContentType("application/json");
			httpPost.setEntity(content);

			response					= httpclient.execute(httpPost, context);
			statusCode					= response.getStatusLine().getStatusCode();
			responseBody				= EntityUtils.toString(response.getEntity());
			
			ObjectMapper	mapper		= new ObjectMapper();
			
			if (statusCode == HttpStatus.SC_OK) {
				JsonNode jsonNode		= mapper.readTree(responseBody);
				getAnalysisResult(jsonNode);
			}else {
				if (configuration.isFailBuildOnCriticalPluginError()) {
					throw new RunBuildException(RunnerConstants.ERROR_FAIL_MESSAGE + responseBody);
				}else {
					runnerContext.getBuild().getBuildLogger().error(RunnerConstants.ERROR_MESSAGE + responseBody);
				}
			}
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			if (configuration.isFailBuildOnCriticalPluginError()) {
				throw new RunBuildException(RunnerConstants.ERROR_FAIL_MESSAGE + responseBody);
			}else {
				runnerContext.getBuild().getBuildLogger().error(RunnerConstants.ERROR_MESSAGE + responseBody);
			}
		} catch (IOException e) {
			e.printStackTrace();
			if (configuration.isFailBuildOnCriticalPluginError()) {
				throw new RunBuildException(RunnerConstants.ERROR_FAIL_MESSAGE + responseBody);
			}else {
				runnerContext.getBuild().getBuildLogger().error(RunnerConstants.ERROR_MESSAGE + responseBody);
			}
		}
  }
	
	/**
	 * Get Analysis Result
	 * @param jsonNode2 
	 * 
	 * @throws RunBuildException
	 */
	public void getAnalysisResult(JsonNode checkerData) throws RunBuildException {
	  	String url 						= RunnerConstants.CARBONETES_ANALYSIS_RESULT;
	  	
		httpPost 						= new HttpPost(url);
		
		jsonBody						= new JSONObject();
		httpclient						= HttpClientBuilder.create().build();
		context							= HttpClientContext.create();
		
		runnerContext.getBuild().getBuildLogger().message("Generating Reports...");
		
		try {
			String			body		= checkerData.toString();
			StringEntity    content;	
			
			content = new StringEntity(body);
			content.setContentType("application/json");
			
			httpPost.setEntity(content);
			response					= httpclient.execute(httpPost, context);
			statusCode					= response.getStatusLine().getStatusCode();
			responseBody				= EntityUtils.toString(response.getEntity());
			
			ObjectMapper	mapper		= new ObjectMapper();
			JsonNode		jsonNode	= null;
			
			jsonNode					= mapper.readTree(responseBody);
			
			
			
			if (statusCode == HttpStatus.SC_OK) {
				runnerContext.getBuild().getBuildLogger().message("===========Analysis Result=========");
				
				policyResult 			= jsonNode.findPath(RunnerConstants.REPO_IMAGE_ENVIRONMENTS)
						.findPath(RunnerConstants.POLICY_EVALUATION_LATEST).findPath("policyResult").asText();
				finalAction 			= jsonNode.findPath(RunnerConstants.REPO_IMAGE_ENVIRONMENTS)
				        .findPath(RunnerConstants.POLICY_EVALUATION_LATEST).findPath("finalAction").asText();
				 
				runnerContext.getBuild().getBuildLogger().message("Policy Result : " + policyResult);
				runnerContext.getBuild().getBuildLogger().message("Final Action : " + finalAction);
					
				String workingDirectoryPath = runnerContext.getWorkingDirectory().getAbsolutePath();
				String jsonContent 			= jsonNode.toString();
				String path 				= workingDirectoryPath + "/" + RunnerConstants.ARTIFACTS;

				Files.write( Paths.get(path), jsonContent.getBytes(StandardCharsets.UTF_8));
				
				artifactsWatcher.addNewArtifactsPath(path);
				
				if (configuration.isFailBuildOnPolicyEvaluationFinalResult() && policyResult.equalsIgnoreCase("failed")) {
					throw new RunBuildException(RunnerConstants.ERROR_ON_FAIL_POLICY_EVALUATION);
				} else {	
					if (policyResult.toString().equalsIgnoreCase("failed")) {
							runnerContext.getBuild().getBuildLogger().error(RunnerConstants.POLICY_EVALUATION_IGNORED);
					}
				}
			}else {
				if (configuration.isFailBuildOnCriticalPluginError()) {
					throw new RunBuildException(RunnerConstants.ERROR_FAIL_MESSAGE + responseBody);
				}else {
					runnerContext.getBuild().getBuildLogger().error(RunnerConstants.ERROR_MESSAGE + responseBody);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			if (configuration.isFailBuildOnCriticalPluginError()) {
				throw new RunBuildException(RunnerConstants.ERROR_FAIL_MESSAGE + responseBody);
			}else {
				runnerContext.getBuild().getBuildLogger().error(RunnerConstants.ERROR_MESSAGE + responseBody);
			}
		}
	}
}	
