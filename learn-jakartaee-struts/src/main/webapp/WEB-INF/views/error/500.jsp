<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insert definition="base-layout">
	<tiles:put name="body" type="string">
			<div class="row">
				<div class="col-sm-6 offset-sm-3 text-center">
					<h1 class="display-4">Kaboom!</h1>
					<div>Terribly sorry... something went horribly wrong with your request.</div>
				</div>
			</div>
	</tiles:put>
</tiles:insert>
