package io.teamcity.plugins.carbonetes;

import java.util.Collections;

import org.jetbrains.annotations.NotNull;

import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.artifacts.ArtifactsWatcher;
import jetbrains.buildServer.agent.runner.BuildServiceAdapter;
import jetbrains.buildServer.agent.runner.ProgramCommandLine;
import jetbrains.buildServer.agent.runner.SimpleProgramCommandLine;

public class CarbonetesService extends BuildServiceAdapter{
	
	protected ArtifactsWatcher artifactsWatcher;
	
	public CarbonetesService(ArtifactsWatcher artifactsWatcher) {
		this.artifactsWatcher = artifactsWatcher;
	}

	@NotNull
	@Override
	public ProgramCommandLine makeProgramCommandLine() throws RunBuildException {
		
		final String username 								  = getRunnerParameters().get(RunnerConstants.USERNAME);
		final String password 								  = getRunnerParameters().get(RunnerConstants.PASSWORD);
		final String registryUri 							  = getRunnerParameters().get(RunnerConstants.REGISTRY_URI);
		final String image 									  = getRunnerParameters().get(RunnerConstants.REPO_IMAGE);
		final String policyBundleId							  = getRunnerParameters().get(RunnerConstants.POLICY_BUNDLE_UUID);
		final String timeout								  = getRunnerParameters().get(RunnerConstants.TIMEOUT);
		final int engineTimeout								  = timeout == null ? RunnerConstants.ENGINE_TIMEOUT : tryParseInt(timeout) ? Integer.parseInt(timeout) : RunnerConstants.ENGINE_TIMEOUT;
		final boolean failBuildOnPolicyEvaluationFinallResult = getRunnerParameters().get(RunnerConstants.FAIL_ON_POLICY_EVALUATION) == null ? false : true;
		final boolean failBuildOnCriticalPluginError 		  = getRunnerParameters().get(RunnerConstants.FAIL_ON_PLUGIN_ERROR) == null ? false : true;
		
		Configuration configuration = new Configuration(username, password, registryUri, image, policyBundleId, 
				engineTimeout, failBuildOnPolicyEvaluationFinallResult, failBuildOnCriticalPluginError);
		
		CarbonetesAPI carbonetesAPI = new CarbonetesAPI(configuration, getRunnerContext(), artifactsWatcher);
		
		carbonetesAPI.initialize();
		
		carbonetesAPI.performComprehensiveAnalysis();
		
		return new SimpleProgramCommandLine(getRunnerContext(), "pwd", Collections.<String>emptyList());
	}
	
	boolean tryParseInt(String value) {  
	     try {  
	         Integer.parseInt(value);  
	         return true;  
	      } catch (NumberFormatException e) {  
	         return false;  
	      }  
	}

}
