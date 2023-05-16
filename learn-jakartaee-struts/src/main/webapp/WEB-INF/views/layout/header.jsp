<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <a class="navbar-brand" href="<c:url value="/index.jsp"/>"><c:out value="${pageTitle}" /></a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
    <div class="navbar-nav">
      <a class="nav-item nav-link <c:if test="${mainNavBarSelected == 'true'}">active</c:if>" href="<c:url value="/aircraft.do"/>">Main <c:if test="${mainNavBarSelected == 'true'}"><span class="sr-only">(current)</span></c:if></a>
      <a class="nav-item nav-link <c:if test="${adminNavBarSelected == 'true'}">active</c:if>" href="<c:url value="/events.do"/>">Admin <c:if test="${adminNavBarSelected == 'true'}"><span class="sr-only">(current)</span></c:if></a>
    </div>
  </div>
</nav>