<%@ tag pageEncoding="utf-8" dynamic-attributes="dynattrs" trimDirectiveWhitespaces="true" %>
<%@ attribute name="title" required="false" %>
<%@ attribute name="head" fragment="true" %>
<%@ attribute name="body" fragment="true" required="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="${pageContext.request.locale}">
<h:head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:out value="${title}"/></title>
    <!-- bootstrap loaded from content delivery network -->
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css"  crossorigin="anonymous">
    <jsp:invoke fragment="head"/>
</h:head>
<body>
    <div class="container">
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="/pa165"><f:message key="app.name"/></a>
                </div>
                <div id="navbar" class="navbar-collapse collapse">
                    <ul class="nav navbar-nav">
                        <li><a href="/pa165"><f:message key="home"/></a></li>
                        <li><a href="/pa165/pokemonSpecies/list"><f:message key="pokemon.species"/></a></li>
                        <li><a href="/pa165/pokemon/list"><f:message key="pokemon"/></a></li>
                        <li><a href="/pa165/gym/list"><f:message key="gyms"/></a></li>
                        <li><a href="/pa165/trainer/list"><f:message key="trainers"/></a></li>
                        <li><a href="/pa165/badge/list"><f:message key="badges"/></a></li>
                        <security:authorize access="!isAuthenticated()">
                            <li><a href="/pa165/login"><f:message key="login"/></a></li>
                        </security:authorize>
                        <security:authorize access="isAuthenticated()">
                            <c:set var="userId"><security:authentication property="principal.trainerId"/></c:set>
                            <li><a href="/pa165/trainer/detail/${userId}"><f:message key="about.me"/></a></li>
                            <li><a href="/pa165/logout"><f:message key="log.out"/></a></li>
                        </security:authorize>
                    </ul>
                </div>
            </div>
        </nav>


    <c:if test="${not empty alert_danger}">
        <div class="alert alert-danger" role="alert">
            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
            <c:out value="${alert_danger}"/></div>
    </c:if>
    <c:if test="${not empty alert_info}">
        <div class="alert alert-info" role="alert"><c:out value="${alert_info}"/></div>
    </c:if>
    <c:if test="${not empty alert_success}">
        <div class="alert alert-success" role="alert"><c:out value="${alert_success}"/></div>
    </c:if>
    <c:if test="${not empty alert_warning}">
        <div class="alert alert-warning" role="alert"><c:out value="${alert_warning}"/></div>
    </c:if>

        <jsp:invoke fragment="body"/>
    </div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>
