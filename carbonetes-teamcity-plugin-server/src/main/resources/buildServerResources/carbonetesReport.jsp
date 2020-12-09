<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags" %>

<%--@elvariable id="policyEvaluationLatest" type="java.lang.String"--%>
<%--@elvariable id="imageAnalysisLatest" type="java.lang.String"--%>
<%--@elvariable id="secretAnalysisLatest" type="java.lang.String"--%>
<%--@elvariable id="scaLatest" type="java.lang.String"--%>
<%--@elvariable id="malwareAnalysisLatest" type="java.lang.String"--%>
<%--@elvariable id="licenseFinderLatest" type="java.lang.String"--%>
<%--@elvariable id="fullImageTag" type="java.lang.String"--%>

<html>
<style scoped="">

				.label {
						padding: 2px 10px;
						line-height: 13px;
						color: #ffffff;
						font-weight: 400;
						border-radius: 4px;
						font-size: 75%;
					}
				
				.label-critical,
				.label-high,
				.label-medium,
				.label-low,
				.label-negligible,
				.label-unknown,
				.label-pending,
				.label-inactive,
				.label-all {
					cursor: pointer;
				}
				
				.label-pending {
					background-color: #e8e8e8;
					color: black;
				}
				
				.label-inactive {
					background-color: #ddd;
					color: black;
				}
				
				.label-all {
					border: 1px solid #007bff;
					background-color: white;
					color: black;
				}
				
</style>
<head>
	<%@ include file="header.jsp" %>
	<%@ include file="meta.jsp" %>
</head>
<body>
	<jsp:include page="page-body.jsp"></jsp:include>
	<%@ include file="script.jsp" %> 
</body>
</html>