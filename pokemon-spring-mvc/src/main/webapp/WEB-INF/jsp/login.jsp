<%-- 
    Document   : Jsp page for login form.
    Author     : Tibor Zauko 433531
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false"
         session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<fmt:setLocale value="${pageContext.request.locale}"/>
<fmt:setBundle basename="Texts"/>
<fmt:message var="title" key="gym"/>
<my:pagetemplate title="${title}">
    <jsp:attribute name="body">

        <c:if test="${param.error ne null}">
            <div class="alert alert-warning" role="alert"><f:message key="user.password.incorrect"/></div>
        </c:if>
        <c:if test="${param.logout ne null}">
            <div class="alert alert-info" role="alert"><f:message key="logout.success"/></div>
        </c:if>
        <center>
            <h1><f:message key="login"/></h1>
            <form name='f' action="login" method='POST'>
                <table>
                    <tr>
                        <td><f:message key="user.name"/>:</td>
                        <td><input type='text' name='username' value='' autofocus></td>
                    </tr>
                    <tr>
                        <td><f:message key="password"/>:</td>
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
        </center>

    </jsp:attribute>
</my:pagetemplate>
