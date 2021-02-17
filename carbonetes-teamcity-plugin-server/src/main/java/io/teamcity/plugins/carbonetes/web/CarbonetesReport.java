package io.teamcity.plugins.carbonetes.web;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
	    super("carbonetesReportTab", "Carbonetes Report", manager, buildManager,
	    descriptor.getPluginResourcesPath(RunnerConstants.CARBONETES_REPORT));
	    
	}

	@Override
	protected void fillModel(Map<String, Object> model, SBuild build) {
		
		final BuildArtifacts buildArtifacts 			= build.getArtifacts(BuildArtifactsViewMode.VIEW_DEFAULT);
	    final BuildArtifactHolder artifact 			    = buildArtifacts.findArtifact(RunnerConstants.ARTIFACTS);
	    if (artifact.isAvailable()) {
	    	try {
	    		String 			content 				= StreamUtil.readText(artifact.getArtifact().getInputStream());
	    		
				JsonNode		jsonNode				= null;
				
				ObjectMapper	mapper					= new ObjectMapper();
				
				jsonNode 								= mapper.readTree(content);
				
				model.put(RunnerConstants.POLICY_EVALUATION_RESULT, jsonNode.findPath(RunnerConstants.REPO_IMAGE_ENVIRONMENTS).findPath(RunnerConstants.POLICY_EVALUATION_LATEST).toString());
				model.put(RunnerConstants.IMAGE_ANALYSIS_LATEST, jsonNode.findPath(RunnerConstants.IMAGE_ANALYSIS_LATEST).toString());
				model.put(RunnerConstants.SECRET_ANALYS_LATEST, jsonNode.findPath(RunnerConstants.SECRET_ANALYS_LATEST).toString());
				model.put(RunnerConstants.SCA_LATEST, jsonNode.findPath(RunnerConstants.SCA_LATEST).toString());
				model.put(RunnerConstants.MALWARE_ANALYSIS_LATEST, jsonNode.findPath(RunnerConstants.MALWARE_ANALYSIS_LATEST).toString().isEmpty() ? "{\"malwareAnalysisLatest\":{\"status\":\"\",\"scanResult\":{\"scanResultUuid\":\"\",\"elapsed_time\": null,\"scanned_files\": null,\"scanned_directories\": null,\"data_read\": null,\"data_scanned\": null,\"infectedFiles\":[]},\"id\":\"\"}}" : jsonNode.findPath(RunnerConstants.MALWARE_ANALYSIS_LATEST).toString());
				model.put(RunnerConstants.LICENSE_FINDER_LATEST, jsonNode.findPath(RunnerConstants.LICENSE_FINDER_LATEST).toString());
				model.put(RunnerConstants.IMAGE_NAME, jsonNode.findPath(RunnerConstants.IMAGE_NAME).asText());
				model.put(RunnerConstants.IMAGE_TAG, jsonNode.findPath(RunnerConstants.IMAGE_TAG).asText());
				model.put(RunnerConstants.BOM_ANALYSIS_LATEST, jsonNode.findPath(RunnerConstants.BOM_ANALYSIS_LATEST).toString());
				
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    
	    
	}
}
