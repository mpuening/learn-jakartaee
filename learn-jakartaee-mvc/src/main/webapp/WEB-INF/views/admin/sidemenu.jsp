<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<div>
	<div class="card">
		<div class="card-header">Admin Links</div>
		<div class="card-body">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item"><a id="events-link" class="nav-link"
					href="<c:url value="/mvc/admin/events"/>">Events</a></li>
				<li class="nav-item"><a id="support-link" class="nav-link"
					href="<c:url value="/mvc/admin/support"/>">Support</a></li>
			</ul>
		</div>
	</div>
</div>