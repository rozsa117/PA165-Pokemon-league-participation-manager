<%--
    Document   : Jsp page for listing all trainers.
    Author     : Michal Mokros 456442
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
<fmt:setBundle basename="Texts"/>
<fmt:message var="title" key="ttrainers"/>
<my:pagetemplate title="${title}">
    <jsp:attribute name="body">
        <h1><fmt:message key="trainer"/> ${trainer.userName}</h1>
        <div class="table-responsive">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th><fmt:message key="trainer.username"/></th>
                        <th><fmt:message key="trainer.name"/></th>
                        <th><fmt:message key="trainer.surname"/></th>
                        <th><fmt:message key="trainer.born"/></th>
                        <th><fmt:message key="trainer.admin"/></th>
                        <th><!--Button Column--></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${allTrainers}" var="trainer">
                        <tr onclick="window.location = '/pa165/trainer/detail/${trainer.id}'" style="cursor: pointer;">
                            <td><c:out value="${trainer.userName}"/></td>
                            <td><c:out value="${trainer.name}"/></td>
                            <td><c:out value="${trainer.surname}"/></td>
                            <td><javatime:format value="${trainer.born}" /></td>
                            <td><c:out value="${trainer.admin}"/></td>
                            <c:set var="userId"><security:authentication property="principal.trainerId"/></c:set>                <security:authorize access="hasRole('ADMIN')">
                                <td>
                                    <c:choose>
                                        <c:when test="${trainer.admin}">
                                            <my:extraTag href="/admin/trainer/unsetAdmin/${trainer.id}" class='btn btn-primary'>
                                                <span class="glyphicon glyphicon-edit"></span>
                                                <fmt:message key="trainer.unsetAdmin"/>
                                            </my:extraTag>
                                        </c:when>
                                        <c:otherwise>
                                            <my:extraTag href="/admin/trainer/setAdmin/${trainer.id}" class='btn btn-primary'>
                                                <span class="glyphicon glyphicon-edit"></span>
                                                <fmt:message key="trainer.setAdmin"/>
                                            </my:extraTag>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </security:authorize>
                            <c:if test="${trainer.id==userId}">
                                <td>
                                    <my:extraTag href="/trainer/rename/${trainer.id}" class='btn btn-primary'>
                                        <span class="glyphicon glyphicon-edit"></span>
                                        <fmt:message key="trainer.rename"/>
                                    </my:extraTag>
                                </td>
                                <td>
                                    <my:extraTag href="/trainer/changePassword/${trainer.id}" class='btn btn-primary'>
                                        <span class="glyphicon glyphicon-edit"></span>
                                        <fmt:message key="trainer.changePassword"/>
                                    </my:extraTag>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <security:authorize access="hasRole('ADMIN')">
            <my:extraTag href="/admin/trainer/new" class="btn btn-default">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                <fmt:message key="trainer.create.new"/>
            </my:extraTag>
        </security:authorize>

    </jsp:attribute>
</my:pagetemplate>

