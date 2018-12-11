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
            
            <tr onclick="window.location='/pa165/gym/detail/${gymBadge.key.id}'" style="cursor: pointer;">
                <td><c:out value="${gymBadge.key.location}"/></td>
                <td><c:out value="${gymBadge.key.type}"/></td>
                <td>
                    <my:extraTag href="/trainer/detail/${gymBadge.key.gymLeader.id}" class='btn btn-primary'>
                        <span class="glyphicon glyphicon-align-justify"></span>
                    </my:extraTag>
                    <c:out value="${gymBadge.key.gymLeader.name} ${gymBadge.key.gymLeader.surname}"/>
                </td>
                <c:choose>
                    <c:when test="${gymBadge.value == null}">
                <td>
                    <my:extraTag href="/badge/new?gym=${gymBadge.key.id}" class='btn btn-primary'>
                        <span class="glyphicon glyphicon-plus"></span> 
                        <fmt:message key="gym.badge.create.new"/>
                    </my:extraTag>
                </td>
                    </c:when>
                    <c:otherwise>
                <td>
                    <my:extraTag href="/badge/detail/${gymBadge.value.id}" class='btn btn-primary'>
                        <span class="glyphicon glyphicon-align-justify"></span> 
                        <fmt:message key="gym.list.badge.view"/>
                    </my:extraTag>
                </td>
                    </c:otherwise>
                </c:choose>
            </tr>
        </c:forEach>
        </tbody>
    </table>
        
</jsp:attribute> 
</my:pagetemplate>

