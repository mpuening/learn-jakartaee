<%@tag description="Header" pageEncoding="UTF-8"%>
<%@attribute name="mainNavBarSelected" fragment="false"%>
<%@attribute name="adminNavBarSelected" fragment="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="header">
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<a id="app-name" class="navbar-brand" href="<c:url value="/"/>">Learn Jakarta EE JSP and Servlet</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNavAltMarkup">
			<div class="navbar-nav">
				<c:if test="${mainNavBarSelected!=null and mainNavBarSelected=='true'}">
					<a href="<c:url value="/aircraft"/>"
						class="nav-item nav-link active">Main<span class="sr-only">(current)</span>
					</a>
				</c:if>
				<c:if test="${mainNavBarSelected==null or mainNavBarSelected!='true'}">
					<a href="<c:url value="/aircraft"/>" class="nav-item nav-link">Main</a>
				</c:if>

				<c:if test="${adminNavBarSelected!=null and adminNavBarSelected=='true'}">
					<a href="<c:url value="/events"/>" class="nav-item nav-link active">Admin<span
						class="sr-only">(current)</span>
					</a>
				</c:if>
				<c:if test="${adminNavBarSelected==null or adminNavBarSelected!='true'}">
					<a href="<c:url value="/events"/>" class="nav-item nav-link">Admin</a>
				</c:if>
			</div>
		</div>
	</nav>
</div>