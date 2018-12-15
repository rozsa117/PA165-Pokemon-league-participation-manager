<%-- 
    Document   : Jsp page for listing all gyms.
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
<fmt:setLocale value="${pageContext.request.locale}"/>
<fmt:setBundle basename="Texts"/>
<fmt:message var="title" key="gym"/>
<my:pagetemplate title="${title}">
<jsp:attribute name="body">
    <h1><fmt:message key="gym"/> ${gym.location}</h1>
    <table class="table">
        <thead>
        <tr>
            <th><fmt:message key="gym.location"/></th>
            <th><fmt:message key="gym.type"/></th>
            <th><fmt:message key="gym.leader"/></th>
            <th><!--Button Column--></th>
        </tr>
        </thead>
         <tbody>
        <c:forEach items="${allGymBadgePairs}" var="gymBadge">
            
            <tr onclick="window.location='/pa165/gym/detail/${gymBadge.gym.id}'" style="cursor: pointer;">
                <td><c:out value="${gymBadge.gym.location}"/></td>
                <td><c:out value="${gymBadge.gym.type}"/></td>
                <td>
                    <my:extraTag href="/trainer/detail/${gymBadge.gym.gymLeader.id}" class='btn btn-primary'>
                        <span class="glyphicon glyphicon-align-justify"></span>
                    </my:extraTag>
                    <c:out value="${gymBadge.gym.gymLeader.name} ${gymBadge.gym.gymLeader.surname}"/>
                </td>
                <td>
                <security:authorize access="hasRole('ADMIN')">
                    <my:extraTag href="/admin/gym/changeLeader?gym=${gymBadge.gym.id}" class='btn btn-primary'>
                        <span class="glyphicon glyphicon-edit"></span> 
                        <fmt:message key="gym.edit.admin"/>
                    </my:extraTag>
                </security:authorize>
                <c:choose>
                    <c:when test="${gymBadge.badge == null}">
                        <c:set var="userId"><security:authentication property="principal.trainerId"/></c:set>
                        <c:choose>
                            <c:when test="${gymBadge.gym.gymLeader.id == userId}">
                    <my:extraTag href="/gym/edit?gym=${gymBadge.gym.id}" class='btn btn-primary'>
                        <span class="glyphicon glyphicon-edit"></span> 
                        <fmt:message key="gym.edit.leader"/>
                    </my:extraTag>
                            </c:when>
                            <c:otherwise>
                    <my:extraTag href="/badge/new?gym=${gymBadge.gym.id}" class='btn btn-primary'>
                        <span class="glyphicon glyphicon-plus"></span> 
                        <fmt:message key="gym.badge.create.new"/>
                    </my:extraTag>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                    <my:extraTag href="/badge/detail/${gymBadge.badge.id}" class='btn btn-primary'>
                        <span class="glyphicon glyphicon-align-justify"></span> 
                        <fmt:message key="gym.list.badge.view"/>
                    </my:extraTag>
                    </c:otherwise>
                </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
        
</jsp:attribute> 
</my:pagetemplate>

