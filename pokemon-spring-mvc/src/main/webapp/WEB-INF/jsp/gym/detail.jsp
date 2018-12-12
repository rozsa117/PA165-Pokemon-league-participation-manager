<%-- 
    Document   : Jsp page fog detailed information about a specific pokemon species.
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
            <td><c:out value="${gym.gymLeader.name} ${gym.gymLeader.surname}"/></td>
        </tr>
        </tbody>
    </table>
        <security:authorize access="hasRole('ADMIN')">
            <my:extraTag href="/admin/gym/changeLeader?gym=${gymBadge.gym.id}" class='btn btn-primary'>
                <span class="glyphicon glyphicon-edit"></span> 
                <fmt:message key="gym.edit.admin"/>
            </my:extraTag>
        </security:authorize>
    <c:choose>
        <c:when test="${badge == null}">
            <c:set var="userId"><security:authentication property="principal.trainerId"/></c:set>
            <c:choose>
                <c:when test="${gym.gymLeader.id == userId}">
                    <my:extraTag href="/gym/changeType/${gym.id}" class='btn btn-primary'>
                        <span class="glyphicon glyphicon-edit"></span> 
                        <fmt:message key="gym.edit.leader"/>
                    </my:extraTag>
                </c:when>
                <c:otherwise>
                    <my:extraTag href="/badge/new?gym=${gym.id}" class="btn btn-primary">
                        <span class="glyphicon glyphicon-plus"></span> 
                        <fmt:message key="gym.badge.create.new"/>
                    </my:extraTag>
                </c:otherwise>
            </c:choose>
        </c:when>
        <c:otherwise>
            <my:extraTag href="/badge/detail/${badge.id}" class="btn btn-primary">
                        <span class="glyphicon glyphicon-eye-open"></span> 
                <fmt:message key="badge.view"/>
            </my:extraTag>
        </c:otherwise>
    </c:choose>
</jsp:attribute>
</my:pagetemplate>
