<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insert definition="base-layout">
	<tiles:put name="body" type="string">
		<h1>Page Not Found</h1>
		<a href="<c:url value="/"/>">Home Page</a>
	</tiles:put>
</tiles:insert>
