<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<tiles:useAttribute name="pageTitle" scope="request" />
<tiles:useAttribute name="mainNavBarSelected" scope="request" />
<tiles:useAttribute name="adminNavBarSelected" scope="request" />
<tiles:useAttribute name="disableSideMenu" scope="request" />
<html>
<head>
<tiles:insert attribute="head" />
</head>

<body id="app">
	<tiles:insert attribute="header" />

	<c:if test="${disableSideMenu == null || disableSideMenu == 'false'}">
		<div class="container-fluid">
			<div class="row">
				<div class="col-md-2">
					<br />
					<tiles:insert attribute="sidemenu" />
				</div>
				<div class="col-md-10">
					<br />
					<tiles:insert attribute="body" />
				</div>
			</div>
		</div>
	</c:if>
	<c:if test="${disableSideMenu != null && disableSideMenu == 'true'}">
		<div class="container-fluid">
			<div class="row">
				<div class="col-md-12">
					<br />
					<tiles:insert attribute="body" />
				</div>
			</div>
		</div>
	</c:if>

</body>
</html>