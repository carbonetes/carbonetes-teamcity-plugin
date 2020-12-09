package io.teamcity.plugins.carbonetes;

import org.jetbrains.annotations.NotNull;

import jetbrains.buildServer.agent.AgentBuildRunnerInfo;
import jetbrains.buildServer.agent.BuildAgentConfiguration;
import jetbrains.buildServer.agent.runner.CommandLineBuildService;
import jetbrains.buildServer.agent.runner.CommandLineBuildServiceFactory;

public class CarbonetesFactory implements CommandLineBuildServiceFactory {
	
	
	@NotNull
	@Override
	public CommandLineBuildService createService() {
		
		return new CarbonetesService();
	}

	@NotNull
	@Override
	public AgentBuildRunnerInfo getBuildRunnerInfo() {
		
		return new AgentBuildRunnerInfo() {
		      @NotNull
		      @Override
		      public String getType() {
		        return RunnerConstants.RUNNER_TYPE;
		      }

		      @Override
		      public boolean canRun(@NotNull BuildAgentConfiguration agentConfiguration) {
		        return true;
		      }
		};
	}

}
