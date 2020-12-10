package io.teamcity.plugins.carbonetes;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;

import jetbrains.buildServer.agent.BuildRunnerContext;
import jetbrains.buildServer.agent.artifacts.ArtifactsWatcher;

/**
 * Contains REST API Setups
 */
public abstract class AbstractAPIWorker extends CarbonetesService {

	public AbstractAPIWorker(Configuration configuration, BuildRunnerContext runnerContext,
			ArtifactsWatcher artifactsWatcher) {
		super(artifactsWatcher);
		
	}

	protected JSONObject			jsonBody;
	protected HttpClientContext		context;
	protected CloseableHttpResponse	response;
	protected int					statusCode;
	protected String				responseBody;
	protected CloseableHttpClient	httpclient;
	protected Configuration			configuration;
	protected HttpPost				httpPost;
	protected HttpGet				httpGet;
	protected BuildRunnerContext	runnerContext;
	protected ArtifactsWatcher artifactsWatcher;
	/**
	 * Initialization for REST API call
	 * 
	 * @throws AbortException
	 */
	protected void initializeAPICall() {
		jsonBody	= new JSONObject();
		httpclient	= HttpClientBuilder.create().build();
		context		= HttpClientContext.create();
	}
}
