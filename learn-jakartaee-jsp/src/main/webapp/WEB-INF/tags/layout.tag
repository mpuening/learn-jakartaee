<%@tag description="Page Layout" pageEncoding="UTF-8"%>

<%@attribute name="mainNavBarSelected" fragment="false"%>
<%@attribute name="adminNavBarSelected" fragment="false"%>

<%@attribute name="disableSideMenu" fragment="false"%>
<%@attribute name="sidemenu" fragment="true"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/tags.tld" prefix="t" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Learn Jakarta EE</title>

<link rel="stylesheet" href="<c:url value="/webjars/bootstrap/4.4.1/css/bootstrap.css"/>" />
<script src="<c:url value="/webjars/jquery/3.4.1/jquery.min.js"/>"></script>
<script src="<c:url value="/webjars/bootstrap/4.4.1/js/bootstrap.min.js"/>"></script>
</head>

<body>
	<layout:header>
		<jsp:attribute name="mainNavBarSelected"><%=mainNavBarSelected%></jsp:attribute>
		<jsp:attribute name="adminNavBarSelected"><%=adminNavBarSelected%></jsp:attribute>
	</layout:header>
	<c:if test="${disableSideMenu == null || disableSideMenu == 'false'}">
		<div class="container-fluid">
			<div class="row">
				<div class="col-md-2">
					<br />
					<jsp:invoke fragment="sidemenu" />
				</div>
				<div class="col-md-10">
					<br />
					<jsp:doBody/>
				</div>
			</div>
		</div>
	</c:if>
	<c:if test="${disableSideMenu != null && disableSideMenu == 'true'}">
		<div class="container-fluid">
			<div class="row">
				<div class="col-md-12">
					<br />
					<jsp:doBody/>
				</div>
			</div>
		</div>
	</c:if>
	<br/>
	<br/>
	<i><t:today/></i>
</body>
</html>