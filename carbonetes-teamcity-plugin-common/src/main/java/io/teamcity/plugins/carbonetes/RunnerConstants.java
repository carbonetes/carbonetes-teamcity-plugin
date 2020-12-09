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
	public static final String ERROR_MESSAGE						= "Carbonetes-plugin marked the build as failed due to error(s).";
	
	public static final String REGISTRY_URI							= "registryUri";
	public static final String REPO_IMAGE						    = "repoImage";
	public static final String REPO_IMAGE_TAG					    = "repoImageTag";
	public static final String USERNAME								= "username";
	public static final String PASSWORD								= "password";
	
	//JSON  FIELDS
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
	
	public static final String ARTIFACTS							= "artifacts.json";

	public static final String CARBONETES_REPORT					= "carbonetesReport.jsp";
	
	public static final int ENGINE_TIMEOUT							= 500;
	public static final long MILLISECONDS							= 1000;
	
	// API END POINTS
	public static final String CARBONETES_ANALYSIS_CHECKER			= "https://api.carbonetes.com/analyze";
	public static final String CARBONETES_ANALYSIS_STATUS			= "https://api.carbonetes.com/check-result";
	public static final String CARBONETES_ANALYSIS_RESULT			= "https://api.carbonetes.com/get-result";
	
	//
}
