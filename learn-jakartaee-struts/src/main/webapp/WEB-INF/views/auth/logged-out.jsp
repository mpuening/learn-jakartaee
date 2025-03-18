<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insert definition="base-layout">
	<tiles:put name="body" type="string">
		<div class="row">
			<div class="col-sm-6 offset-sm-3 text-center">
				<h1 class="display-4">Goodbye</h1>
				<p>
					You have been logged out
				</p>
				<p>
					<a class="nav-item nav-link" href="<c:url value="/index.jsp"/>">Sign In</a>
				</p>
			</div>
		</div>
	</tiles:put>
</tiles:insert>