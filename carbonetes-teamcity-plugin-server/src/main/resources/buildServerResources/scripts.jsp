<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="forms" tagdir="/WEB-INF/tags/forms" %>

<script src="${teamcityPluginResourcesPath}resources/js/vue/vue.js"></script>
<script src="${teamcityPluginResourcesPath}resources/js/vuetify/vuetify.js"></script>
<script src="${teamcityPluginResourcesPath}resources/js/lodash/lodash.min.js"></script>
<script>
    new Vue({
      el: '#app',
      vuetify: new Vuetify(),
      data: function() {	
						return  {
							pr : {
							scaHeaders: [
								{ text: 'Dependency',value: 'fileName', sortable: true},
								{ text: 'File Path', value: 'filePath' , sortable: false},
								{ text: 'Vulnerabilities', value: 'vulnerabilities' , sortable: false},
								{ text: 'Evidence Collected', value: 'evidenceColllected' , sortable: false},
								],
							licenseFinderHeaders: [
								{ text: 'Dependency', sortable: true},
								{ text: 'Version', sortable: false},
								{ text: 'Licenses', sortable: false},
								{ text: 'Link', sortable: false}
							],
							secretsHeaders: [
								{ text: 'File', value: 'fileName', sortable: true},
								{ text: 'File Path',value: 'filePath', sortable: true},
								{ text: 'Line No. ', value: 'lineNo',sortable: true},
								{ text: 'Content Regex Match',value: 'contentRegexMatch', sortable: true},
								
							],
							malwareHeaders:[
								{ text: 'File Directory', value: 'fileName', sortable: true},
								{ text: 'File Name', value: 'fileName', sortable: true},
								{ text: 'Virus', value: 'fileName', sortable: true}
							],
							policyHeaders:[
								{ text: 'Gate', value: 'gate', sortable: true},
								{ text: 'Gate Trigger', value: 'gateTrigger', sortable: true},
								{ text: 'Check Output', value: 'checkOutput', sortable: true},
								{ text: 'Gate Action', value: 'gateAction', sortable: true},
							],	
							dedupHeaders: [
								{ text: 'Severity',value: 'severity', sortable: true, align : 'center'},
								{ text: 'Vulnerability', value: 'vuln' , sortable: true},
								{ text: 'Package Name', value: 'package_name' , sortable: true},
								{ text: 'Package Version', value: 'package_version' , sortable: true},
								{ text: 'Fix', value: 'fix' , sortable: true},
								],
							bomHeaders: [
								{text: 'Name', align: 'left', value: 'name',sortable: true},
			                    {text: 'Version', align: 'left', value: 'version',sortable: true},
			                    {text: 'Type', align: 'left', value: 'type',sortable: true},
			                    {text: 'Path', align: 'left', value: 'value',sortable: true},
							],
							tabItems : [
								{ tab: 'Vulnerability', content: 'Tab 1 Content' },
								{ tab: 'Software Composition', content: 'Tab 2 Content' },
								{ tab: 'Secrets', content: 'Tab 2 Content' },
								{ tab: 'License Finder', content: 'Tab 2 Content' },
								{ tab: 'Malware Analysis', content: 'Tab 2 Content' },			   
							],
							tab : null,	    
							rowsPerPageOptions : { 
								rowsPerPageItems: [5, 10, 20]
                            },
                            vulnerabilityResult 	: ${imageAnalysisLatest},
                            scaResult 				: ${scaLatest},
                            malwareResult 			: ${malwareAnalysisLatest},
                            licenseResult 			: ${licenseFinderLatest},
                            secretsResult 			: ${secretAnalysisLatest},
                            policyEvaluationResult  : ${policyEvaluationResult},
                            bomResult				: ${billOfMaterialsAnalysisLatest},
                            searchDetails : '',
								filters: { 
										categories: ['All', 'Critical', 'High', 'Medium', 'Low', 'Negligible', 'Unknown'],
										selectedItemIndex: 0
										},
							}
						} 				      
          },
          watch: {
							"pr.pagination.page": (newPage) => {
								console.log(newPage);
							}
            },
            computed: {
						pDuration:{
							get() {
								return parseInt(this.pr.scaResult.analysis.duration / 60);
							}
						},
						pScaResult : {
							get() {
								return this.pr.scaResult
							}
						},
						pLicenseFinderResult : {
							get() {
								return this.pr.licenseResult
							}
						},
						pSecretsResult: {
							get(){
								return this.pr.secretsResult
							}
						},
						pMalwareResult: {
							get(){
								return this.pr.malwareResult
							}
						},
						pVulnerabilityResult: {
								get() {
									return this.pr.vulnerabilityResult
								},
							},
						pVulnerability: {
								get() {
									return this.pr.vulnerability
								},
							},
						pPolicyEvaluationResult: {
								get() {
									return this.pr.policyEvaluationResult
								}
							},
						pBomResult: {
								get(){
									return this.pr.bomResult
								}
							},
						
						filterButtons: {
							get() {
								let ref = this;
					
								return ref.pr.filters.categories.map(function(categ) {
								let isSelected =  (ref.pr.filters.categories.indexOf(categ) == ref.pr.filters.selectedItemIndex);
										
										let temp =  {
										'text': categ,
											'class': {
												'label': true,
												'label-rounded': true
												}
										};
									temp['class']['label-' + categ.toLowerCase()] = isSelected;
									temp['class']['label-inactive'] = !isSelected;
									return temp;
									})
							}
						},
          },
          
          methods: {
        	  
        	  search: _.debounce(function (e) {
					let ref = this;
					console.log(e);
				}, 600),
        
          setFilter: function(category) {
              
              let ref = this;
        
              ref.pr.search = category;
              let index 	= ref.pr.filters.categories.indexOf(category);
              
              ref.pr.filters.selectedItemIndex = index;
            },
        }
    })
  </script>