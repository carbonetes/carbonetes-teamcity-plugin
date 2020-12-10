<%@ page import="io.teamcity.plugins.carbonetes.RunnerConstants" %>
<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags" %>

<c:set var="registryUri" value="<%=RunnerConstants.REGISTRY_URI%>"/>
<c:set var="repoImage" value="<%=RunnerConstants.REPO_IMAGE%>"/>
<c:set var="policyBundleId" value="<%=RunnerConstants.POLICY_BUNDLE_UUID%>"/>
<c:set var="engineTimeOut" value="<%=RunnerConstants.TIMEOUT%>"/>
<c:set var="failOnPolicyEvaluation" value="<%=RunnerConstants.FAIL_ON_POLICY_EVALUATION%>"/>
<c:set var="failOnPluginError" value="<%=RunnerConstants.FAIL_ON_PLUGIN_ERROR%>"/>
<c:set var="username" value="<%=RunnerConstants.USERNAME%>"/>
<c:set var="password" value="<%=RunnerConstants.PASSWORD%>"/>

<l:settingsGroup title="Carbonetes Build Options">
  <tr>
      <th><label for="${registryUri}">Registry URI: <l:star/></label></th>
      <td>
         <props:textProperty name="${registryUri}" className="longField"/>
         <span class="error" id="error_${registryUri}"></span>
      </td>
    </tr>
  <tr>
      <th><label for="${repoImage}">Repo Image and Tag: <l:star/></label></th>
      <td>
         <props:textProperty name="${repoImage}" className="longField"/>
         <span class="error" id="error_${repoImage}"></span>
      </td>
   </tr>
  <tr>
  <tr>
      <th><label>Carbonetes Engine Policy Bundle ID:</label></th>
      <td>
         <props:textProperty name="${policyBundleId}" className="longField"/>
      </td>
   </tr>
  <tr>
      <th><label for="${engineTimeOut}">Carbonetes Engine Timeout: <l:star/></label></th>
      <td>
         <props:textProperty name="${engineTimeOut}" className="longField"/>
         <span class="error" id="error_${engineTimeOut}"></span>
      </td>
   </tr>
  <tr>
  <tr>
    <th><label>Fail Build on Policy Evaluation FAIL result</label></th>
    <td>
      <props:checkboxProperty name="${failOnPolicyEvaluation}"/>
    </td>
  </tr>
  <tr>
    <th><label>Fail Build on Critical Plugin Error</label></th>
    <td>
      <props:checkboxProperty name="${failOnPluginError}"/>
    </td>
  </tr>
</l:settingsGroup>

<l:settingsGroup title="Carbonetes Engine Credentials">
  <tr>
    <th><label for="${username}">Username: <l:star/></label></th>
    <td>
         <props:textProperty name="${username}" className="longField"/>
         <span class="error" id="error_${username}"></span>
      </td>
  </tr>
  <tr>
    <th><label for="${password}">Password: <l:star/></label></th>
    <td>
         <props:passwordProperty name="${password}" className="longField"/>
         <span class="error" id="error_${password}"></span>
     </td>
  </tr>
</l:settingsGroup>