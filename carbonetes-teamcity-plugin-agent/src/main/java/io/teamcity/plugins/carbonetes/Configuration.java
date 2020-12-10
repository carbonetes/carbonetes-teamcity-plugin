package io.teamcity.plugins.carbonetes;

/**
 * Contains Plugin Configuration Parameters
 */
public class Configuration {
	private String			username;
	private String			password;
	private String			registryUri;
	private String			image;
	private String			policyBundleId;
	private int				engineTimeout;
	private boolean			failBuildOnPolicyEvaluationFinallResult;
	private boolean			failBuildOnCriticalPluginError;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRegistryUri() {
		return registryUri;
	}

	public void setRegistryUri(String registryUri) {
		this.registryUri = registryUri;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public String getPolicyBundleID() {
		return policyBundleId;
	}

	public void setPolicyBundleID(String policyBundleId) {
		this.policyBundleId = policyBundleId;
	}
	
	public int getEngineTimeout() {
		return engineTimeout;
	}

	public void setEngineTimeout(int engineTimeout) {
		this.engineTimeout = engineTimeout;
	}
	
	public boolean isFailBuildOnPolicyEvaluationFinalResult() {
		return failBuildOnPolicyEvaluationFinallResult;
	}

	public void setFailBuildOnPolicyEvaluationFinallResult(boolean failBuildOnPolicyEvaluationFinallResult) {
		this.failBuildOnPolicyEvaluationFinallResult = failBuildOnPolicyEvaluationFinallResult;
	}
	
	public boolean isFailBuildOnCriticalPluginError() {
		return failBuildOnCriticalPluginError;
	}
	
	public void setFailBuildOnCriticalPluginError(boolean failBuildOnCriticalPluginError) {
		this.failBuildOnCriticalPluginError = failBuildOnCriticalPluginError;
	}
	
	public Configuration(String username, String password, String registryUri, String image, String policyBundleId,
			int engineTimeout, boolean failBuildOnPolicyEvaluationFinallResult, boolean failBuildOnCriticalPluginError) {
		this.username									= username;
		this.password									= password;
		this.registryUri								= registryUri;
		this.image										= image;
		this.policyBundleId								= policyBundleId;
		this.engineTimeout								= engineTimeout;
		this.failBuildOnPolicyEvaluationFinallResult	= failBuildOnPolicyEvaluationFinallResult;
		this.failBuildOnCriticalPluginError				= failBuildOnCriticalPluginError;
	}
}
