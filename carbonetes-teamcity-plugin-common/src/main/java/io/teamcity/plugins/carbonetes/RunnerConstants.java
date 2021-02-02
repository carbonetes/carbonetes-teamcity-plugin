package io.teamcity.plugins.carbonetes;

/**
 * 
 * Common plugin constants
 * 
 * @author carbonetes
 *
 */
public final class RunnerConstants {
	// RUNNER REGISTRY CONSTANTS
	public static final String RUNNER_TYPE							= "carbonetesRunner";
	public static final String MESSAGE_KEY							= "Scan Image";
	public static final String DISPLAY_NAME 						= "Carbonetes";
	public static final String DESCRIPTION							= "Carbonetes Comprehensive Analysis";

	
	
	
	// ERROR MESSAGES
	public static final String POLICY_EVALUATION_IGNORED 			= "Ignored Policy Evaluation result.";
	public static final String ERROR_ON_FAIL_POLICY_EVALUATION		= "Carbonetes-plugin marked the build as failed due to Policy Evaluation Failed result.";
	public static final String ERROR_FAIL_MESSAGE					= "Carbonetes-plugin marked the build as failed due to error(s). ";
	public static final String ERROR_MESSAGE						= "Errors Encountered : ";
	
	public static final String SUBCRIPTION_LINK						= "Please configure your AWS Contract Licenses at https://aws.amazon.com/marketplace/saas/ordering?productId=1bede3a0-aff1-4a89-ad92-baab6b8b4e70";
	
	public static final String ERROR_LICENSE_EXPIRED				= "License has already expired.";
	public static final String MSG_LICENSE_EXPIRED					= "Carbonetes License Expired.";
	public static final String LINK_LICENSE_EXPIRED					= "Your contract licenses has expired.";
	
	public static final String ERROR_INSUFFICIENT_LICENSE			= "Insufficent license count.";
	public static final String MSG_INSUFFICIENT_LICENSE				= "Insufficient Carbonetes licenses.";
	public static final String LINK_LICENSE_INSUFFICIENT			= "Your contract licenses is insufficient for your current users.";
	
	// JSON  FIELDS
	public static final String REGISTRY_URI							= "registryUri";
	public static final String REPO_IMAGE						    = "repoImage";
	public static final String REPO_IMAGE_TAG					    = "repoImageTag";
	public static final String USERNAME								= "username";
	public static final String PASSWORD								= "password";
	public static final String TIMEOUT								= "timout";
	public static final String POLICY_BUNDLE_UUID					= "policyBundleUUID";
	public static final String POLICY_EVALUATION_RESULT				= "policyEvaluationResult";
	public static final String POLICY_EVALUATION_LATEST				= "policyEvaluationLatest";
	public static final String IMAGE_ANALYSIS_LATEST				= "imageAnalysisLatest";
	public static final String SECRET_ANALYS_LATEST					= "secretAnalysisLatest";
	public static final String SCA_LATEST							= "scaLatest";
	public static final String MALWARE_ANALYSIS_LATEST				= "malwareAnalysisLatest";
	public static final String LICENSE_FINDER_LATEST				= "licenseFinderLatest";
	public static final String REPO_IMAGE_ENVIRONMENTS				= "repoImageEnvironments";
	public static final String FULL_IMAGE_TAG						= "fullImageTag";
	public static final String REPO_TAG								= "repoTag";
	public static final String FAIL_ON_POLICY_EVALUATION			= "failBuildOnPolicyEvaluationFinallResult";
	public static final String FAIL_ON_PLUGIN_ERROR					= "failBuildOnCriticalPluginError";
	
	// REPORTS
	public static final String ARTIFACTS							= "carbo-artifacts.json";
	public static final String CARBONETES_REPORT					= "carbonetesReport.jsp";
	public static final int ENGINE_TIMEOUT							= 500;
	public static final long MILLISECONDS							= 1000;
	
	// API END POINTS
	public static final String CARBONETES_ANALYSIS_CHECKER			= "https://api.carbonetes.com/api/v1/analysis/analyze";
	public static final String CARBONETES_ANALYSIS_STATUS			= "https://api.carbonetes.com/api/v1/analysis/check-result";
	public static final String CARBONETES_ANALYSIS_RESULT			= "https://api.carbonetes.com/api/v1/analysis/get-result";
	
}
