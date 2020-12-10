<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<jsp:useBean id="constants" class="io.teamcity.plugins.carbonetes.RunnerConstants"/>

<div class="parameter">
    Active policy bundle: <props:displayValue name="${constants.POLICY_BUNDLE_ID}" emptyValue="No policy bundle configured"/>
</div>

<div class="parameter">
    Fail build on policy evaluation: <props:displayValue name="${constants.FAIL_ON_POLICY_EVALUATION}" emptyValue=""/>
</div>

<div class="parameter">
    Fail build on plugin error: <props:displayValue name="${constants.FAIL_ON_PLUGIN_ERROR}" emptyValue=""/>
</div>