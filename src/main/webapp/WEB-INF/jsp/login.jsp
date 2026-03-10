<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="parts/headcontents.jsp" />
</head>
<body>
  <div id="wrap">
      <div class="container" id="main" style="margin-top: 10px">   
      <div class="container center-block" style="text-align: center">
        <c:if test="${param.error !=null}">
          <div class="alert alert-danger" role="alert">
           Invalid username or password
            <button type="button" class="close" data-dismiss="alert">
              <span aria-hidden="true">&times;</span>
              <span class="sr-only">Close</span>
            </button>
          </div>
        </c:if>
        <div class="row">
          <div>
            <div class="panel panel-default">
              <div class="panel-heading">
                <h3 class="panel-title">Please sign in</h3>
              </div>
              <div class="panel-body">
                <form accept-charset="UTF-8" role="form" action="authenticate" method="post">
                  <fieldset>
                    <div class="form-group">
                      <input class="form-control" placeholder="Username" name="username" type="text">
                    </div>
                    <div class="form-group">
                      <input class="form-control" placeholder="Password" name="password" type="password" value="">
                    </div>
                    <input class="btn btn-lg btn-success btn-block" type="submit" value="Login">
                  </fieldset>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>