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
<head>
	<style>
		<%@ include file="resources/css/style.css" %>			
	</style>
	<%@ include		file="header.jsp" %>
	<%@ include 	file="meta.jsp" %>
</head>
<body>
	<jsp:include 	page="page-body.jsp"></jsp:include>
	<%@ include 	file="scripts.jsp" %> 
</body>
</html>