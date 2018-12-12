<%-- 
    Document   : Jsp page for login form.
    Author     : Tibor Zauko 433531
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false"
         session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="Texts"/>
<!DOCTYPE html>
<html lang="${pageContext.request.locale}">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><f:message key="login"/></title>
    <!-- bootstrap loaded from content delivery network -->
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css"  crossorigin="anonymous">
</head>
<body>    
    <c:if test="${param.error ne null}">
        <div class="alert alert-warning" role="alert"><f:message key="user.password.incorrect"/></div>
    </c:if>
    <c:if test="${param.logout ne null}">
        <div class="alert alert-info" role="alert"><f:message key="logout.success"/></div>
    </c:if>
   <h1><f:message key="login"/></h1>
   <form name='f' action="login" method='POST'>
      <table>
         <tr>
            <td>User:</td>
            <td><input type='text' name='username' value=''></td>
         </tr>
         <tr>
            <td>Password:</td>
            <td><input type='password' name='password' /></td>
         </tr>
         <tr>
            <td>
                <button type="submit" class="btn btn-primary" style="margin-top: 10px;">
                    <span class="glyphicon glyphicon-ok"></span> 
                    <f:message key="login"/>
                </button>
            </td>
         </tr>
      </table>
  </form>
</body>
</html>
