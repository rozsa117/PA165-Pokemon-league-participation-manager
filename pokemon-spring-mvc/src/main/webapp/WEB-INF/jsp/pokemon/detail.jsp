<%-- 
    Document   : Jsp page fog detailed information about pokemons.
    Author     : Jiří Medveď 38451
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false"
         session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<fmt:setBundle basename="Texts"/>
<fmt:message var="title" key="gym"/>
<my:pagetemplate title="${title}">
<jsp:attribute name="body">
    <h1><fmt:message key="gym"/> ${gym.location}</h1>
    <table class="table">
        <tbody>
        <tr>
            <th><fmt:message key="gym.type"/></th>
            <td><c:out value="${gym.type}"/></td>
        </tr>

        <tr onclick="window.location='/pa165/trainer/detail/${gym.gymLeader.id}'" style="cursor: pointer;">
            <th><fmt:message key="gym.leader"/></th>
            <td><c:out value="${gym.gymLeader}"/></td>
        </tr>
        </tbody>
    </table>
    <c:choose>
        <c:when test="${badge == null}">
            <my:extraTag href="/badge/new?gym=${gym.id}" class="btn btn-default">
                <fmt:message key="gym.badge.create.new"/>
            </my:extraTag>
        </c:when>
        <c:otherwise>
            <my:extraTag href="/badge/detail/${badge.id}" class="btn btn-default">
                <fmt:message key="badge.view"/>
            </my:extraTag>
        </c:otherwise>
    </c:choose>
</jsp:attribute>
</my:pagetemplate>
