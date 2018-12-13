<%-- 
    Document   : Jsp home page.
    Author     : Tamás Rózsa 445653
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false"
         session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<fmt:setBundle basename="Texts"/>
<fmt:message var="title" key="pokemon.species"/>
<my:pagetemplate title="${title}">
    <jsp:attribute name="body">
        
        <c:if test="${param.logout ne null}">
            <div class="alert alert-info" role="alert"><f:message key="logout.success"/></div>
        </c:if>
        
        <center>
        <h1 style="font-size:70px"><fmt:message key="main.page.title.text"/></h1>
        <p style="font-size:50px"><fmt:message key="main.page.text"/></p>
        <a href="https://github.com/rozsa117/PA165-Pokemon-league-participation-manager" style="font-size:50px"><fmt:message key="project.page"/></a>
        <p><font color="red" style="font-size:50px"><fmt:message key="catch.them.all"/></font></p>
        </center>
    </jsp:attribute>
</my:pagetemplate>