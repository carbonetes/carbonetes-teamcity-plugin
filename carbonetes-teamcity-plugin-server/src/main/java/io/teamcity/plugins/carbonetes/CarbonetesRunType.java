package io.teamcity.plugins.carbonetes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import jetbrains.buildServer.serverSide.InvalidProperty;
import jetbrains.buildServer.serverSide.PropertiesProcessor;
import jetbrains.buildServer.serverSide.RunType;
import jetbrains.buildServer.serverSide.RunTypeRegistry;
import jetbrains.buildServer.web.openapi.PluginDescriptor;

public class CarbonetesRunType extends RunType{
	
	private final PluginDescriptor descriptor;

	public CarbonetesRunType(RunTypeRegistry registry, PluginDescriptor descriptor) {
	    this.descriptor = descriptor;
	    registry.registerRunType(this);
	}
	
	@NotNull
	@Override
	public String getType() {
		return RunnerConstants.RUNNER_TYPE;
	}
	
	@NotNull
	@Override
	public String getDisplayName() {
		
		return RunnerConstants.DISPLAY_NAME;
	}
	
	@NotNull
	@Override
	public String getDescription() {
		
		return RunnerConstants.DESCRIPTION;
	}

	@NotNull
	@Override
	public PropertiesProcessor getRunnerPropertiesProcessor() {
		
		return properties -> {
		      final List<InvalidProperty> invalidProperties = new ArrayList<>();
		      
		      final String registryUri 		= properties.get(RunnerConstants.REGISTRY_URI);
		      final String repoImage 		= properties.get(RunnerConstants.REPO_IMAGE);
		      final String username 		= properties.get(RunnerConstants.USERNAME);
		      final String password			= properties.get(RunnerConstants.PASSWORD);
		      final String engineTimeOut	= properties.get(RunnerConstants.TIMEOUT);
		      
		      if (registryUri == null) {
		    	  invalidProperties.add(new InvalidProperty(RunnerConstants.REGISTRY_URI, "Registry URI is required"));
			  }
		      
		      if (repoImage == null) {
		    	  invalidProperties.add(new InvalidProperty(RunnerConstants.REPO_IMAGE, "Image is required"));
		      }else if (!repoImage.contains(":")) {
		    	  invalidProperties.add(new InvalidProperty(RunnerConstants.REPO_IMAGE, "Invalid Image"));
		      }else if (repoImage.contains(":")) {
		    	  try {
		    		  if (repoImage.split(":")[0] == null || repoImage.split(":")[1] == null) {
				    	  invalidProperties.add(new InvalidProperty(RunnerConstants.REPO_IMAGE, "Invalid Image"));
					  }
		    	  }catch (Exception e){
		    		  invalidProperties.add(new InvalidProperty(RunnerConstants.REPO_IMAGE, "Invalid Image"));
		    		  e.printStackTrace();
		    	  }
		      }
		      
		      if (username == null) {
		    	  invalidProperties.add(new InvalidProperty(RunnerConstants.USERNAME, "Username is required"));
			  }
		      
		      if (password == null) {
		    	  invalidProperties.add(new InvalidProperty(RunnerConstants.PASSWORD, "Password is required"));
			  }
		      
		      if (engineTimeOut == null) {
		    	  invalidProperties.add(new InvalidProperty(RunnerConstants.TIMEOUT, "Engine Timeout is required"));
		      }else {
		    	  if (!engineTimeOut.matches("-?(0|[1-9]\\d*)")) {
			    	  invalidProperties.add(new InvalidProperty(RunnerConstants.TIMEOUT, "Invalid number"));
			      }
		      }
		      
		     

		      return invalidProperties;
		    };
	}

	@NotNull
	@Override
	public String getEditRunnerParamsJspFilePath() {
		 
		return descriptor.getPluginResourcesPath("editCarbonetesRunnerParameters.jsp");
	}

	@NotNull
	@Override
	public String getViewRunnerParamsJspFilePath() {
		
		return descriptor.getPluginResourcesPath("viewCarbonetesRunnerParameters.jsp");
	}

	@NotNull
	@Override
	public Map<String, String> getDefaultRunnerProperties() {
		Map<String, String> defaultProperties = new HashMap<>(2);
	    defaultProperties.put(RunnerConstants.FAIL_ON_POLICY_EVALUATION, "false");
	    defaultProperties.put(RunnerConstants.FAIL_ON_PLUGIN_ERROR, "false");
	    return defaultProperties;
	}
	
	@NotNull	
	@Override
	public String describeParameters(@NotNull Map<String, String> parameters) {
		String policyBundleId =  parameters.get(RunnerConstants.POLICY_BUNDLE_UUID) == null ? "No policy bundle configured" : parameters.get(RunnerConstants.POLICY_BUNDLE_UUID);
	    String failOnPolicyEvaluation = parameters.get(RunnerConstants.FAIL_ON_POLICY_EVALUATION) == null ?  "OFF" : "ON";
	    String failOnPluginError = parameters.get(RunnerConstants.FAIL_ON_PLUGIN_ERROR) == null ? "OFF" : "ON";
	    return String.format("Active policy bundle: %s%nFail build on policy evaluation: %s%nFail build on plugin error: %s",policyBundleId , failOnPolicyEvaluation, failOnPluginError);
	}

}
