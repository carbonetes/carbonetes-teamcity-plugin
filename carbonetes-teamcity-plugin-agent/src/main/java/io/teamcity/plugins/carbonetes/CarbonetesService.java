package io.teamcity.plugins.carbonetes;

import java.util.Collections;

import org.jetbrains.annotations.NotNull;

import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.runner.BuildServiceAdapter;
import jetbrains.buildServer.agent.runner.ProgramCommandLine;
import jetbrains.buildServer.agent.runner.SimpleProgramCommandLine;

public class CarbonetesService extends BuildServiceAdapter{
	@NotNull
	@Override
	public ProgramCommandLine makeProgramCommandLine() throws RunBuildException {
		
		final String username 								  = getRunnerParameters().get(RunnerConstants.USERNAME);
		final String password 								  = getRunnerParameters().get(RunnerConstants.PASSWORD);
		final String registryUri 							  = getRunnerParameters().get(RunnerConstants.REGISTRY_URI);
		final String image 									  = getRunnerParameters().get(RunnerConstants.REPO_IMAGE);
		final boolean failBuildOnPolicyEvaluationFinallResult = getRunnerParameters().get("failBuildOnPolicyEvaluationFinallResult") == null ? false : true;
		final boolean failBuildOnCriticalPluginError 		  = getRunnerParameters().get("failBuildOnCriticalPluginError") == null ? false : true;
		
		Configuration configuration = new Configuration(username, password, registryUri, image, 
				failBuildOnPolicyEvaluationFinallResult, failBuildOnCriticalPluginError);
		
		CarbonetesAPI carbonetesAPI = new CarbonetesAPI(configuration, getRunnerContext());
		
		carbonetesAPI.initialize();
		
		carbonetesAPI.performComprehensiveAnalysis();
		
		carbonetesAPI.getAnalysisResult();
		
		return new SimpleProgramCommandLine(getRunnerContext(), "pwd", Collections.<String>emptyList());
	}

}
