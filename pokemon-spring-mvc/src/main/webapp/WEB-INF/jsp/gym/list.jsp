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
<fmt:setBundle basename="Types" var = "t"/>
<fmt:message var="title" key="gyms"/>
<my:pagetemplate title="${title}">
    <jsp:attribute name="body">
        <h1><fmt:message key="gyms"/> ${gym.location}</h1>
        <div class="table-responsive">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th><fmt:message key="gym.location"/></th>
                        <th><fmt:message key="gym.type"/></th>
                        <th><fmt:message key="gym.leader"/></th>
                        <th colspan="2"><!--Button Column--></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${allGymBadgePairs}" var="gymBadge">

                        <tr onclick="window.location = '/pa165/gym/detail/${gymBadge.gym.id}'" style="cursor: pointer;">
                            <td><c:out value="${gymBadge.gym.location}"/></td>
                            <td><fmt:message bundle="${t}" key="${empty gymBadge.gym.type? 'empty' : gymBadge.gym.type}"/></td>
                            <td>
                                <my:extraTag href="/trainer/detail/${gymBadge.gym.gymLeader.id}" class='btn btn-primary'>
                                    <span class="glyphicon glyphicon-eye-open"></span>
                                </my:extraTag>
                                <c:out value="${gymBadge.gym.gymLeader.name} ${gymBadge.gym.gymLeader.surname}"/>
                            </td>
                            <td>
                                <security:authorize access="hasRole('ADMIN')">
                                    <my:extraTag href="/admin/gym/changeLeader/${gymBadge.gym.id}" class='btn btn-primary'>
                                        <span class="glyphicon glyphicon-edit"></span> 
                                        <fmt:message key="gym.edit.admin"/>
                                    </my:extraTag>
                                </security:authorize>
                                <c:set var="userId"><security:authentication property="principal.trainerId"/></c:set>
                                <c:choose>
                                    <c:when test="${gymBadge.badge == null}">
                                        <c:if test="${gymBadge.gym.gymLeader.id ne userId}">
                                            <my:extraTag href="/badge/create?gymId=${gymBadge.gym.id}" class='btn btn-primary'>
                                                <span class="glyphicon glyphicon-plus"></span> 
                                                <fmt:message key="gym.badge.create.new"/>
                                            </my:extraTag>
                                        </c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <my:extraTag href="/badge/detail/${gymBadge.badge.id}" class='btn btn-primary'>
                                            <span class="glyphicon glyphicon-eye-open"></span> 
                                            <fmt:message key="gym.list.badge.view"/>
                                        </my:extraTag>
                                    </c:otherwise>
                                </c:choose>
                                <c:if test="${gymBadge.gym.gymLeader.id eq userId}">
                                    <my:extraTag href="/gym/changeType/${gymBadge.gym.id}" class='btn btn-primary'>
                                        <span class="glyphicon glyphicon-edit"></span> 
                                        <fmt:message key="gym.edit.leader"/>
                                    </my:extraTag>
                                    <my:extraTag href="/badge/challenges" class='btn btn-primary'>
                                        <span class="glyphicon glyphicon-edit"></span> 
                                        <fmt:message key="gym.show.challenges"/>
                                    </my:extraTag>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>   
        </div>
        <security:authorize access="hasRole('ADMIN')">
            <my:extraTag href="/admin/gym/create" class='btn btn-primary'>
                <span class="glyphicon glyphicon-edit"></span> 
                <fmt:message key="gym.create.new"/>
            </my:extraTag>
        </security:authorize>

    </jsp:attribute> 
</my:pagetemplate>

