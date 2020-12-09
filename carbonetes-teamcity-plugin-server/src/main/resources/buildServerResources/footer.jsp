<script src="https://cdn.jsdelivr.net/npm/vue@2.x/dist/vue.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vuetify@2.x/dist/vuetify.js"></script>
<script src="https://cdn.jsdelivr.net/npm/lodash@4.17.15/lodash.min.js"></script>
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
								{ text: 'Trigger ID', value: 'triggerId', sortable: true},
								{ text: 'Gate', value: 'gate', sortable: true},
								{ text: 'Gate Trigger', value: 'gateTrigger', sortable: true},
								{ text: 'Check Output', value: 'checkOutput', sortable: true},
								{ text: 'Gate Action', value: 'gateAction', sortable: true},
							],	
							dedupHeaders: [
								{ text: 'Severity',value: 'severity', sortable: false, align : 'center'},
								{ text: 'Vulnerability', value: 'vuln' , sortable: false},
								{ text: 'Package Name', value: 'package_name' , sortable: false},
								{ text: 'Package Version', value: 'package_version' , sortable: false},
								{ text: 'Fix', value: 'fix' , sortable: false},
								{ text: 'Feed', value: 'feed' , sortable: false},
								{ text: 'Feed Group', value: 'feed_group' , sortable: false},
								{ text: 'Gate Action', value: 'gate_action' , sortable: false},
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
                            vulnerabilityResult : ${imageAnalysisLatest},
                            scaResult : ${scaLatest},
                            malwareResult : ${malwareAnalysisLatest},
                            licenseResult : ${licenseFinderLatest},
                            secretsResult : ${secretAnalysisLatest},
                            policyEvaluationResult : ${policyEvaluationResult}, 
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