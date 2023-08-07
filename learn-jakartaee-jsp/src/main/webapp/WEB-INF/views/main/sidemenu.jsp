<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div>
	<div class="card">
		<div class="card-header">Main Links</div>
		<div class="card-body">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item"><a id="people-link" class="nav-link"
					href="<c:url value="/hello"/>">Hello</a></li>
				<li class="nav-item"><a id="aircraft-link" class="nav-link"
					href="<c:url value="/aircraft"/>">Aircraft</a></li>
				<li class="nav-item"><a id="help-link" class="nav-link"
					href="<c:url value="/help"/>">Help</a></li>
			</ul>
		</div>
	</div>
</div>