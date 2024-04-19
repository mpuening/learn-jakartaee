<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<div>
	<div class="card">
		<div class="card-header">Main Links</div>
		<div class="card-body">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item"><a id="aircraft-link" class="nav-link"
					href="<c:url value="/mvc/main/aircraft"/>">Aircraft</a></li>
				<li class="nav-item"><a id="help-link" class="nav-link"
					href="<c:url value="/mvc/main/help"/>">Help</a></li>
			</ul>
		</div>
	</div>
</div>