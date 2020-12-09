package io.teamcity.plugins.carbonetes.web;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.openapi.util.io.StreamUtil;

import io.teamcity.plugins.carbonetes.RunnerConstants;
import jetbrains.buildServer.serverSide.BuildsManager;
import jetbrains.buildServer.serverSide.SBuild;
import jetbrains.buildServer.serverSide.artifacts.BuildArtifactHolder;
import jetbrains.buildServer.serverSide.artifacts.BuildArtifacts;
import jetbrains.buildServer.serverSide.artifacts.BuildArtifactsViewMode;
import jetbrains.buildServer.web.openapi.BuildTab;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import jetbrains.buildServer.web.openapi.WebControllerManager;

public class CarbonetesReport extends BuildTab  {
	protected CarbonetesReport(WebControllerManager manager, BuildsManager buildManager, PluginDescriptor descriptor) {
	    super("carbonetesReportTab", "Carbonetes Analysis Report", manager, buildManager,
	    descriptor.getPluginResourcesPath(RunnerConstants.CARBONETES_REPORT));
	    
	}

	@Override
	protected void fillModel(Map<String, Object> model, SBuild build) {
		
		final BuildArtifacts buildArtifacts 			= build.getArtifacts(BuildArtifactsViewMode.VIEW_DEFAULT);
	    final BuildArtifactHolder artifact 			    = buildArtifacts.findArtifact(RunnerConstants.ARTIFACTS);
	    if (artifact.isAvailable()) {
	    	try {
				String 			content 				= StreamUtil.readText(artifact.getArtifact().getInputStream());
				JsonNode		policyEvaluationLatest	= null;
				JsonNode		imageAnalysisLatest		= null;
				JsonNode		secretAnalysisLatest	= null;
				JsonNode		scaLatest				= null;
				JsonNode		malwareAnalysisLatest	= null;
				JsonNode		licenseFinderLatest		= null;
				JsonNode		fullImageTag			= null;
				
				ObjectMapper	mapper					= new ObjectMapper();
				
				policyEvaluationLatest					= mapper.readTree(content);
				imageAnalysisLatest						= mapper.readTree(content);
				secretAnalysisLatest					= mapper.readTree(content);
				scaLatest								= mapper.readTree(content);
				malwareAnalysisLatest					= mapper.readTree(content);
				licenseFinderLatest						= mapper.readTree(content);
				fullImageTag							= mapper.readTree(content);
				
				model.put(RunnerConstants.POLICY_EVALUATION_RESULT, policyEvaluationLatest.findPath(RunnerConstants.REPO_IMAGE_ENVIRONMENTS).findPath(RunnerConstants.POLICY_EVALUATION_LATEST).toString());
				model.put(RunnerConstants.IMAGE_ANALYSIS_LATEST, imageAnalysisLatest.findPath(RunnerConstants.IMAGE_ANALYSIS_LATEST).toString());
				model.put(RunnerConstants.SECRET_ANALYS_LATEST, secretAnalysisLatest.findPath(RunnerConstants.SECRET_ANALYS_LATEST).toString());
				model.put(RunnerConstants.SCA_LATEST, scaLatest.findPath(RunnerConstants.SCA_LATEST).toString());
				model.put(RunnerConstants.MALWARE_ANALYSIS_LATEST, malwareAnalysisLatest.findPath(RunnerConstants.MALWARE_ANALYSIS_LATEST).toString());
				model.put(RunnerConstants.LICENSE_FINDER_LATEST, licenseFinderLatest.findPath(RunnerConstants.LICENSE_FINDER_LATEST).toString());
				model.put(RunnerConstants.FULL_IMAGE_TAG, fullImageTag.findPath(RunnerConstants.REPO_TAG).asText());
				
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	}
	
	
	static String readFile(String path, Charset encoding)
			  throws IOException
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, encoding);
			}

}
