<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="parts/headcontents.jsp" />
</head>
<body>
	<div class="container">
		<form id="file-form" action="submit" method="post">
			<div class="log-out-form">
				<button formaction="logout" id="logout" formnovalidate
					class="btn btn-danger btn-lg log-out-btn">
					<span class="glyphicon glyphicon-off"></span>
				</button>
				<div class="username">
					<!--  <h4>${user.getUsername()}</h4> -->
					<h4>${loginUser.userName}</h4>
				</div>
			</div>
			<c:if test="${param.success ==true}">
				<div class="alert alert-success">
					<strong>Success!</strong> Your changes have been successfully
					committed.
					<button type="button" class="close" data-dismiss="alert">
						<span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
					</button>
				</div>
			</c:if>
			<c:if test="${param.success ==false}">
				<div class="alert alert-danger">
					<strong>Error!</strong> Your changes could not be committed.
					<button type="button" class="close" data-dismiss="alert">
						<span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
					</button>
				</div>
			</c:if>
			<h3>Übergabe zwischen AE und Softwareverteilung</h3>
			<fieldset>
				<label>Paketname</label> <input name="packetName" id="packetName"
					required value="${formInput.packetName}" type="text">
			</fieldset>
			<fieldset>
				<label>Version</label> <input name="version" id="version"
					value="${formInput.version}" type="text">
			</fieldset>
			<fieldset>
				<label>Paketabhängigkeiten (kommaseparierte Liste)</label> <input name="dependencies" id="dependencies"
					value="${formInput.dependencies}" type="text">
			</fieldset>
			<fieldset>
				<label>nur Update</label> <input name="update"
					type="checkbox" value="${formInput.update}" id="update">
			</fieldset>
			<!--
			<fieldset>
				<label>Verteilart</label> <label class="radio"> <input
					type="radio" name="delivery" value="Serverpaket">Serverpaket
				</label> <label class="radio"> <input type="radio" name="delivery"
					value="Clientpaket">Clientpaket
				</label> <label class="radio"> <input type="radio" name="delivery"
					value="Citrix-TS">Citrix-TS
				</label>
			</fieldset>
			-->
			<fieldset>
				<label>Verteilart</label>
			</fieldset>
			<fieldset>
				<label>Serverpaket</label> <input name="Serverpaket"
					type="checkbox" value="${formInput.serverDeployment}" id="serverpackage">
			</fieldset>
			<fieldset>
				<label>Clientpaket</label> <input name="Clientpaket"
					type="checkbox" value="${formInput.clientDeployment}" id="clientpackage">
			</fieldset>
			<fieldset>
				<label>Citrix-Ts</label> <input name="Citrix-TS"
					type="checkbox" value="${formInput.citrixTsDeployment}" id="citrixtspackage">
			</fieldset>
			<fieldset>
				<label>Kurzbeschreibung</label> <input name="comment" id="comment"
					value="${formInput.comment}" type="text" tabindex="1">
			</fieldset>
			<fieldset>
				<label>Paketart</label> <input type="hidden"
					value="${formInput.packetType}" /> <select name="packetType"
					id="packetType">
					<option value="Release">Release</option>
					<option value="Fehlerbereinigung">Fehlerbereinigung</option>
					<option value="Hotfix">Hotfix</option>
				</select>
			</fieldset>
			<fieldset>
				<label>UNIFA-Installation</label> <input name="unifa"
					type="checkbox" value="${formInput.unifa}" id="unifa">
			</fieldset>
			<fieldset>
				<label>FA-Installation</label> <input name="fa" type="checkbox"
					value="${formInput.fa}" id="fa">
			</fieldset>
			<fieldset>
				<label>Fust-Installation</label> <input name="fust"
					type="checkbox" value="${formInput.fust}" id="fust">
			</fieldset>
			<fieldset>
				<label>GST-Installation</label> <input name="gst"
					type="checkbox" value="${formInput.gst}" id="gst">
			</fieldset>
			<fieldset>
				<label>Sonder PC-Installation</label> <input name="sonderpc" type="checkbox"
					value="${formInput.sonderpc}" id="sonderpc">
			</fieldset>
			<fieldset>
				<label>Entwickler PC-Installation</label> <input name="enticklerpc"
					type="checkbox" value="${formInput.entwicklerpc}" id="entwicklerpc">
			</fieldset>
			<fieldset>
				<label>BAP-Installation</label> <input name="bap" type="checkbox"
					value="${formInput.bap}" id="bap">
			</fieldset>
			<fieldset>
				<label>Prüfer NB-Installation</label> <input name="pruefernb"
					type="checkbox" value="${formInput.pruefernb}" id="other">
			</fieldset>
			<fieldset>
				<label>Kassen NB-Installation</label> <input name="kassennb" type="checkbox"
					value="${formInput.kassennb}" id="kassennb">
			</fieldset>
			<fieldset>
				<label>Vergnügungssteuer NB-Installation</label> <input name="vergnuegungssteuernb"
					type="checkbox" value="${formInput.vergnuegungssteuernb}" id="vergnuegungssteuernb">
			</fieldset>
			<fieldset>
				<label>sonstige Installation</label> <input name="other"
					type="checkbox" value="${formInput.others}" id="other">
			</fieldset>
			<fieldset>
				<button class="formBtn">Submit</button>
				<button formaction="form" class="formBtn" formnovalidate>Cancel</button>
			</fieldset>
		</form>
	</div>
</body>
</html>