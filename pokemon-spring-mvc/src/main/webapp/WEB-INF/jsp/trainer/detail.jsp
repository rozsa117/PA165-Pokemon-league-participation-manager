<%--
    Document   : Jsp page fog detailed information about a specific trainer.
    Author     : Michal Mokros 456442
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<fmt:setBundle basename="Texts"/>
<fmt:message var="title" key="trainer"/>
<my:pagetemplate title="${title}">
<jsp:attribute name="body">
    <h1><fmt:message key="trainer"/> ${trainer.userName}</h1>
    <table class="table">
        <tbody>
        <tr>
            <th><fmt:message key="trainer.username"/></th>
            <td><c:out value="${trainer.userName}"/></td>
        </tr>

        <tr>
            <th><fmt:message key="trainer.name"/></th>
            <td><c:out value="${trainer.name}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="trainer.surname"/></th>
            <td><c:out value="${trainer.surname}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="trainer.born"/></th>
            <td><my:localDate date="${trainer.born}" pattern="dd-MM-yyyy"/></td>
        </tr>
        <tr>
            <th><fmt:message key="trainer.admin"/></th>
            <td><c:out value="${trainer.admin}"/></td>
        </tr>
        </tbody>
    </table>
    <my:extraTag href="/trainer/list" class="btn btn-default">
        <fmt:message key="all.trainers"/>
    </my:extraTag>
</jsp:attribute>
</my:pagetemplate>

