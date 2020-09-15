<%@tag description="Page Layout" pageEncoding="UTF-8"%>
<%@attribute name="header" fragment="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tags.tld" prefix="t" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hello World Jakarta EE</title>
</head>
<body>
	<h1>Hello Jakarta JSP and Servlet</h1>

	<div id="header">
		<jsp:invoke fragment="header" />
	</div>
	<div id="body">
      <jsp:doBody/>
    </div>
	<br/>
	<br/>
	<i><t:today/></i>
</body>
</html>