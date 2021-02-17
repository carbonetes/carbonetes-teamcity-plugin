<div id="app">
        <v-app>
        <template>
            <v-card>
                <v-tabs
                v-model="pr.tab"
                background-color="#0e7984"
                dark=""
                >
                    <v-tab>
                        <v-card-text>Policy Evaluation</v-card-text>
                    </v-tab>
                    <v-tab>
                        <v-card-text>Vulnerability Analysis</v-card-text>
                    </v-tab>
                    <v-tab>
                        <v-card-text>Software Composition Analysis</v-card-text>
                    </v-tab>
                    <v-tab v-if="pMalwareResult">
                        <v-card-text>Malware Analysis</v-card-text>
                    </v-tab>
                    <v-tab>
                        <v-card-text>Secrets Analysis</v-card-text>
                    </v-tab>
                    <v-tab>
                        <v-card-text>License Finder Analysis</v-card-text>
                    </v-tab>
                    <v-tab>
                    	<v-card-text>Bill of Materials</v-card-text>
                    </v-tab>

                    <v-tabs-items
                    v-model="pr.tab">
                        <v-tab-item>
                            <v-card class="mx-auto" outlined="">
                                    <v-container>
                                        <div class="row">
                                        <div class="col-md-3">
                                                <v-card-text ><span class="font-weight-bold">Policy Bundle</span></v-card-text>
                                                <v-divider></v-divider>
                                        </div>
                                        <div class="col-md-4">
                                                <v-card-text>
                                                <span>2ac50459-b732-4959-b778-bd885b88d069</span>
                                                </v-card-text>
                                                <v-divider></v-divider>
                                        </div>
                                        </div>
                                        <div class="row">	
                                            <div class="col-md-3">
                                                    <v-card-text ><span class="font-weight-bold">Status</span></v-card-text>
                                                    <v-divider></v-divider>
                                            </div>
                                            <div class="col-md-4">
                                                    <v-card-text>
                                                    	 <span class="label label-negligible" v-if="pr.policyEvaluationResult.policyResult=='PASSED'"> {{pr.policyEvaluationResult.policyResult}}</span>
                                                    	 <span class="label label-critical" v-if="pr.policyEvaluationResult.policyResult=='FAILED'"> {{pr.policyEvaluationResult.policyResult}}</span>
                                                    </v-card-text>
                                                    <v-divider></v-divider>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-3">
                                                    <v-card-text ><span class="font-weight-bold">Final Action</span></v-card-text>
                                                    <v-divider></v-divider>
                                            </div>
                                            <div class="col-md-4">
                                                    <v-card-text>
                                                    	<span class="label label-critical" v-if="pr.policyEvaluationResult.finalAction=='STOP'"> {{pr.policyEvaluationResult.finalAction}}</span>
                                                    	<span class="label label-negligible" v-if="pr.policyEvaluationResult.finalAction=='GO'"> {{pr.policyEvaluationResult.finalAction}}</span>
                                                    	<span class="label label-medium" v-if="pr.policyEvaluationResult.finalAction=='WARN'"> {{pr.policyEvaluationResult.finalAction}}</span>	
                                                   
                                                    </v-card-text>
                                                    <v-divider></v-divider>
                                            </div>
                                        </div>
                                    </v-container>
                                    <v-card>
                                            <v-container>
                                                <v-card-title>
                                                <span class="font-weight-bold">Results</span>	
                                                </v-card-title>
                                                    <v-data-table
                                                    v-bind:items="pPolicyEvaluationResult.policyEvaluationResults"
                                                    v-bind:headers="pr.policyHeaders"
                                                    class="elevation-1"
                                                    >
                                                        <template v-slot:item="props">
                                                            <tr>
                                                                <td class="text-xs-center">
                                                                    {{props.item.gate}}
                                                                </td>
                                                                <td class="text-xs-center">
                                                                    {{props.item.gateTrigger}}
                                                                </td>
                                                                <td class="text-xs-center">
                                                                    {{props.item.checkOutput}}
                                                                </td>
                                                                <td class="text-xs-center">
                                                                    <span class="label label-critical" v-if="pr.policyEvaluationResult.finalAction=='STOP'"> {{props.item.gateAction}}</span>
                                                                    <span class="label label-negligible" v-if="pr.policyEvaluationResult.finalAction=='GO'"> {{props.item.gateAction}}</span>
                                                                    <span class="label label-medium" v-if="pr.policyEvaluationResult.finalAction=='WARN'"> {{props.item.gateAction}}</span>	
                                                                </td>
                                                            </tr>
                                                        </template>
                                                    </v-data-table>
                                            </v-container>
                                        </v-card>
                            </v-card>
                        </v-tab-item>

                        <v-tab-item>
                            <v-card class="mx-auto" outlined="">	
                                <v-card-title> 
                                    Vulnerabilities Found in ${name}:${tag}
                                </v-card-title>
                                <v-spacer></v-spacer>
                                
                                <v-data-table
                                v-bind:items="pVulnerabilityResult.vulnerabilities"
                                v-bind:headers="pr.dedupHeaders"
                                :items-per-page-options="pr.rowsPerPageOptions"
                                class="elevation-1"
                                >
                                <template v-slot:item="props">
                                    <tr>
                                        <td class="text-xs-center">
                                            <span class="label label-critical label-rounded" v-if="props.item.severity == 'Critical'">Critical</span>
                                            <span class="label label-high label-rounded" v-if="props.item.severity == 'High'">High</span>
                                            <span class="label label-medium label-rounded" v-if="props.item.severity == 'Medium'">Medium</span>
                                            <span class="label label-low label-rounded" v-if="props.item.severity == 'Low'">Low</span>
                                            <span class="label label-negligible label-rounded" v-if="props.item.severity == 'Negligible'">Negligible</span>
                                            <span class="label label-unknown label-rounded" v-if="props.item.severity == 'Unknown'">Unknown</span>
                                        </td>
                                        <td class="text-xs-center">
                                            <a v-bind:href="props.item.url" target="_new">
                                            {{props.item.vuln}}
                                            </a>
                                        </td>
                                        <td class="text-xs-center">
                                            <div v-if="props.item.package_name">
                                                {{props.item.package_name}}
                                            </div>
                                            <div v-else="">
                                            --
                                            </div>
                                        </td>
                                        <td class="text-xs-center">
                                            <div v-if="props.item.package_version">
                                                {{props.item.package_version}}
                                            </div>
                                            <div v-else="">
                                            --
                                            </div>
                                        </td>
                                        <td class="text-xs-center">
                                            <div v-if="props.item.fix">
                                                {{props.item.fix}}
                                            </div>
                                            <div v-else="">
                                            --
                                            </div>
                                        </td>
                                    </tr>
                                </template>
                                </v-data-table>
                            </v-card>	
                        </v-tab-item>

                        <v-tab-item>
                            <v-card class="mx-auto" outlined="">
                                <v-container>
                                
                                        <div class="row">
                                            <div class="col-md-3">
                                                <v-card-text ><span class="font-weight-bold">Image</span></v-card-text>
                                                <v-divider></v-divider>
                                            </div>
                                            <div class="col-md-3">
                                                <v-card-text><span class = "label label-negligible">${name}:${tag}</span></v-card-text>
                                                <v-divider></v-divider>
                                            </div>
                                            <div class="col-md-3">
                                                <v-card-text ><span class="font-weight-bold">Total Dependencies</span></v-card-text>
                                                <v-divider></v-divider>
                                            </div>
                                            <div class="col-md-3">
                                                <v-card-text><span class="label label-negligible">{{pScaResult.analysis.totalDependency}}</span></v-card-text>
                                                <v-divider></v-divider>
                                            </div>
                                        </div>
                                        <div class ="row">
                                            <div class="col-md-3">
                                                <v-card-text>
                                                    <i class="fas fa-bug"></i>
                                                    <span class="font-weight-bold">Vulnerability Found</span>
                                                </v-card-text>
                                                <v-divider></v-divider>
                                            </div>
                                            <div class="col-md-3">
                                                <v-card-text><span class="label label-negligible">{{pScaResult.analysis.vulnerabilityFound}}</span></v-card-text>
                                                <v-divider></v-divider>
                                            </div>
                                            <div class="col-md-3">
                                                <v-card-text>
                                                    <span class="font-weight-bold">Vulnerable Dependencies</span>
                                                </v-card-text>
                                                <v-divider></v-divider>
                                            </div>
                                            <div class="col-md-3">
                                                <v-card-text><span class="label label-negligible">{{pScaResult.analysis.vulnerableDependency}}</span></v-card-text>
                                                <v-divider></v-divider>
                                            </div>
                                        </div>
                                        <div class ="row">
                                            <div class="col-md-3">
                                                <v-card-text>
                                                    <i class="fas fa-stopwatch"></i>
                                                    <span class="font-weight-bold">Duration</span>
                                                </v-card-text>
                                                <v-divider></v-divider>
                                            </div>
                                            <div class="col-md-3">
                                                <v-card-text>
                                                    <span class="label label-negligible">{{pDuration}}m</span>
                                                </v-card-text>
                                                <v-divider></v-divider>
                                            </div>
                                        </div>
                                        
                                        <v-card>
                                            <v-container>
                                                    <v-card-title>
                                                    <span class="font-weight-bold">Dependencies</span>	
                                            </v-card-title>
                                            </v-container>
                                                <v-data-table
                                                v-bind:items="pScaResult.analysis.dependencies"
                                                v-bind:headers="pr.scaHeaders"
                                                class="elevation-1"
                                                >
                                                    <template v-slot:item="props">
                                                        <tr>
                                                            <td class="text-xs-center">
                                                                <a v-bind:href="props.item.fileName" target="_new">
                                                                {{props.item.fileName}}
                                                                </a>
                                                            </td>

                                                            <td class="text-xs-center">
                                                                {{props.item.filePath}}
                                                            </td>
                                                            <td class="text-xs-center">
                                                                --
                                                            </td>
                                                            <td class="text-xs-center">
                                                                --
                                                            </td>
                                                        </tr>
                                                    </template>
                                                </v-data-table>
                                        </v-card>
                                </v-container>
                            </v-card>
                        </v-tab-item>

                        <v-tab-item v-if="pMalwareResult">
                            <div v-if="pMalwareResult.scanResult">
                                <div v-if="pMalwareResult.scanResult.infectedFiles.length > 0">
                                <v-data-table
                                    v-bind:items="pMalwareResult.scanResult.infectedFiles"
                                    v-bind:headers="pr.malwareHeaders">
                                    <template v-slot:item="props">
                                                            <tr>
                                                                <td class="text-xs-center">
                                                                    {{props.item.file_directory}}
                                                                </td>

                                                                <td class="text-xs-center">
                                                                    {{props.item.file_name}}
                                                                </td>
                                                                <td class="text-xs-center">
                                                                    {{props.item.virus}}
                                                                </td>
                                                            </tr>
                                                        </template>	
                                
                                </v-data-table>
                                </div>
                                <div v-else="">
                                    <v-card>
                                            <v-card-title>
                                                <label>
                                                    <i class="far fa-check-circle"></i>
                                                    No threats found.
                                                </label>
                                            </v-card-title>
                                        </v-card>
                                </div>
                            </div>
                            <div v-else="">
                                    <v-card>
                                            <v-card-title>
                                                <label style = "color:red">
                                                    <i class="fas fa-exclamation-circle"></i>
                                                    Malware Analysis Failed.
                                                </label>
                                            </v-card-title>
                                        </v-card>
                                </div>
                        </v-tab-item>

                        <v-tab-item>
                            <div v-if="pSecretsResult.secrets.length > 0">
                                <v-data-table
                                    v-bind:items="pSecretsResult.secrets"
                                    v-bind:headers="pr.secretsHeaders">
                                    <template v-slot:item="props">
                                                            <tr>
                                                                <td class="text-xs-center">
                                                                    {{props.item.fileName}}
                                                                </td>

                                                                <td class="text-xs-center">
                                                                    {{props.item.filePath}}
                                                                </td>
                                                                <td class="text-xs-center">
                                                                    {{props.item.lineNo}}
                                                                </td>
                                                                <td class="text-xs-center">
                                                                    <span class="label label-pending has-tooltip">{{props.item.contentRegexMatch}}</span>
                                                                </td>
                                                            </tr>
                                                        </template>	
                                
                                </v-data-table>
                            </div>
                            <div v-else="">
                                <v-card>
                                    <v-card-title>
                                        No secrets found.
                                    </v-card-title>
                                </v-card>
                            </div>
                        </v-tab-item>

                        <v-tab-item>
                            <div v-if="pLicenseFinderResult.imageDependencies.length > 0">
                            <v-card class ="mx-auto" outlines="">
                                    <v-card-title style = "color:red"> 
                                        License Finder Status : {{pLicenseFinderResult.imageDependencies.length}} for approval dependencies.
                                    </v-card-title>
                                <v-data-table
                                                v-bind:items="pLicenseFinderResult.imageDependencies"
                                                v-bind:headers="pr.licenseFinderHeaders"
                                                class="elevation-1"
                                                >
                                                <template v-slot:item="props">
                                                        <tr>
                                                            <td class="text-xs-center">
                                                                {{props.item.dependencyName}}
                                                            </td>

                                                            <td class="text-xs-center">
                                                                {{props.item.version}}
                                                            </td>
                                                            <td class="text-xs-center">
                                                                <div v-for="license in props.item.licenses">
                                                                    <span>{{license.licenseName+" "}}</span>
                                                                </div>
                                                            </td>
                                                            <td class="text-xs-center">
                                                                <a v-bind:href="props.item.licenses[0].licenseLinks" target="_new">
                                                                    {{props.item.licenses[0].licenseLinks}}
                                                                </a>
                                                            </td>
                                                        </tr>
                                                    </template>	
                                                </v-data-table>
                            </v-card>
                            </div>
                            <div v-else="">
                                <v-card>
                                    <v-card-title style="color:green">
                                        No dependency approval needed
                                    </v-card-title>
                                </v-card>
                            </div>
                        </v-tab-item>
                        
                        <v-tab-item>
                            <v-card class="mx-auto" outlined="">	
                                <v-card-title> 
                                    Bill of Materials - ${name}:${tag}
                                </v-card-title>
                                <v-spacer></v-spacer>
                                
                                <v-data-table
                                v-bind:items="pBomResult.artifacts"
                                v-bind:headers="pr.bomHeaders"
                                :items-per-page-options="pr.rowsPerPageOptions"
                                class="elevation-1"
                                >
                                <template v-slot:item="props">
                                    <tr>
                                        <td class="text-xs-center">
                                        	<div v-if="props.item.name">
                                                {{props.item.name}}
                                            </div>
                                            <div v-else="">
                                            --
                                            </div>
                                        </td>
                                        <td class="text-xs-center">
                                            <div v-if="props.item.version">
                                                {{props.item.version}}
                                            </div>
                                            <div v-else="">
                                            --
                                            </div>
                                        </td>
                                        <td class="text-xs-center">
                                        	<div v-if="props.item.type">
                                                {{props.item.type}}
                                            </div>
                                            <div v-else="">
                                            --
                                            </div>
                                        </td>
                                        <td class="text-xs-center">
                                        	<template v-if="props.item.locations.length > 1">
				                                <v-tooltip top color="#00b09b">
				                                    <template v-slot:activator="{ on }">
				                                        <span v-on="on">
				                                            {{props.item.locations.length}} paths
				                                        </span>
				                                    </template>
				                                    <span>
				                                        <span class="font-weight-bold">Package Paths</span>
				                                        <ul class="mb-0">
				                                            <li v-for="l in props.item.locations">
				                                                <span class="font-weight-bold">{{l.path}}</span>
				                                            </li>
				                                        </ul>
				                                    </span>
				                                </v-tooltip>
				                         
				                            </template>
				                            <template v-else-if="props.item.locations.length == 1">
				                                <a href="javascript:;">
				                                    <span>
				                                        {{props.item.locations[0].path}}
				                                    </span>
				                                </a>
				                            </template>
                                        </td>
                                    </tr>
                                </template>
                                </v-data-table>
                            </v-card>	
                        </v-tab-item>
                        
                    </v-tabs-items>
                </v-tabs>
            </v-card>
        </template>
        </v-app>
    </div>