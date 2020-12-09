<%@ page import="io.teamcity.plugins.carbonetes.RunnerConstants" %>
<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean"/>

<l:settingsGroup title="Carbonetes Build Options">
  <tr>
      <th><label for="registryUri">Registry URI: <l:star/></label></th>
      <td>
         <props:textProperty name="registryUri" className="longField"/>
         <span class="error" id="error_registryUri"></span>
      </td>
    </tr>
  <tr>
      <th><label for="repoImage">Repo Image and Tag: <l:star/></label></th>
      <td>
         <props:textProperty name="repoImage" className="longField"/>
         <span class="error" id="error_repoImage"></span>
      </td>
   </tr>
  <tr>
    <th><label>Fail Build on Policy Evaluation FAIL result</label></th>
    <td>
      <props:checkboxProperty name="failBuildOnPolicyEvaluationFinallResult"/>
    </td>
  </tr>
  <tr>
    <th><label>Fail Build on Critical Plugin Error</label></th>
    <td>
      <props:checkboxProperty name="failBuildOnCriticalPluginError"/>
    </td>
  </tr>
</l:settingsGroup>

<l:settingsGroup title="Carbonetes Engine Credentials">
  <tr>
    <th><label for="username">Username: <l:star/></label></th>
    <td>
         <props:textProperty name="username" className="longField"/>
         <span class="error" id="error_username"></span>
      </td>
  </tr>
  <tr>
    <th><label for="password">Password: <l:star/></label></th>
    <td>
         <props:passwordProperty name="password" className="longField"/>
         <span class="error" id="error_password"></span>
     </td>
  </tr>
</l:settingsGroup>