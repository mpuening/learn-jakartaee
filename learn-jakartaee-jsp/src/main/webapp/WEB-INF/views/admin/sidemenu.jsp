<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div>
	<div class="card">
		<div class="card-header">Admin Links</div>
		<div class="card-body">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item"><a id="events-link" class="nav-link"
					href="<c:url value="/events"/>">Events</a></li>
				<li class="nav-item"><a id="support-link" class="nav-link"
					href="<c:url value="/support"/>">Support</a></li>
			</ul>
		</div>
	</div>
</div>