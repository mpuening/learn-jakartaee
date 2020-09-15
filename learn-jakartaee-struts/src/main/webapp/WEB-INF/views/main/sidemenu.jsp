<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div>
	<div class="card">
		<div class="card-header">Main Links</div>
		<div class="card-body">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item"><a class="nav-link" href="<c:url value="/people.do"/>">People</a></li>
				<li class="nav-item"><a class="nav-link" href="<c:url value="/help.do"/>">Help</a></li>
			</ul>
		</div>
	</div>
</div>