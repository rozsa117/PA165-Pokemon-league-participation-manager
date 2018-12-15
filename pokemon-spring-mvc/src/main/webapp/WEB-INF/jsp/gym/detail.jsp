<%--
    Document   : Jsp page for detailed information about a specific gym.
    Author     : Tibor Zauko 433531
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
<fmt:setBundle basename="Types" var = "t"/>
<fmt:message var="title" key="gym.at.location"><fmt:param value="${gym.location}"/></fmt:message>
<my:pagetemplate title="${title}">
    <jsp:attribute name="body">
        <h1><fmt:message key="gym.at.location"><fmt:param value="${gym.location}"/></fmt:message></h1>
            <div class="table-responsive">
                <table class="table table-hover">
                    <tbody>
                        <tr>
                            <th><fmt:message key="gym.type"/></th>
                        <td><fmt:message bundle="${t}" key="${empty gym.type ? 'empty': gym.type}"/></td>
                    </tr>

                    <tr onclick="window.location = '/pa165/trainer/detail/${gym.gymLeader.id}'" style="cursor: pointer;">
                        <th><fmt:message key="gym.leader"/></th>
                        <td><c:out value="${gym.gymLeader.name} ${gym.gymLeader.surname}"/></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <security:authorize access="hasRole('ADMIN')">
            <my:extraTag href="/admin/gym/changeLeader/${gymBadge.gym.id}" class='btn btn-primary'>
                <span class="glyphicon glyphicon-edit"></span>
                <fmt:message key="gym.edit.admin"/>
            </my:extraTag>
        </security:authorize>
        <c:set var="userId"><security:authentication property="principal.trainerId"/></c:set>
        <c:choose>
            <c:when test="${badge == null}">
                <c:if test="${gym.gymLeader.id != userId}">
                    <my:extraTag href="/badge/new?gym=${gym.id}" class="btn btn-primary">
                        <span class="glyphicon glyphicon-plus"></span>
                        <fmt:message key="gym.badge.create.new"/>
                    </my:extraTag>
                </c:if>
            </c:when>
            <c:otherwise>
                <my:extraTag href="/badge/detail/${badge.id}" class="btn btn-primary">
                    <span class="glyphicon glyphicon-eye-open"></span>
                    <fmt:message key="badge.view"/>
                </my:extraTag>
            </c:otherwise>
        </c:choose>
        <c:if test="${gym.gymLeader.id == userId}">
            <my:extraTag href="/gym/changeType/${gym.id}" class='btn btn-primary'>
                <span class="glyphicon glyphicon-edit"></span>
                <fmt:message key="gym.edit.leader"/>
            </my:extraTag>
            <my:extraTag href="/badge/challenges" class='btn btn-primary'>
                <span class="glyphicon glyphicon-edit"></span>
                <fmt:message key="gym.show.challenges"/>
            </my:extraTag>
        </c:if>
    </jsp:attribute>
</my:pagetemplate>
