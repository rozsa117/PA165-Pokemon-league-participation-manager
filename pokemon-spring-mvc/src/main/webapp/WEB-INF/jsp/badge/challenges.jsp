<%--
    Document   : Jsp page for listing all gym's challenges/badges.
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
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
<fmt:setLocale value="${pageContext.request.locale}"/>
<fmt:setBundle basename="Texts"/>
<fmt:setBundle basename="ChallengeStatus" var = "s"/>
<fmt:message var="title" key="badges"/>
<my:pagetemplate title="${title}">
    <jsp:attribute name="body">
        <h1>
            <fmt:message key="badges.of.entity">
                <fmt:param>
                    <fmt:message key="gym.at.location">
                        <fmt:param value="${gym.location}"/>
                    </fmt:message>
                </fmt:param>
            </fmt:message>
        </h1>
        <c:set var="userId"><security:authentication property="principal.trainerId"/></c:set>
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th><fmt:message key="badge.trainer"/></th>
                        <th><fmt:message key="badge.first.challenged.on"/></th>
                        <th><fmt:message key="badge.status"/></th>
                        <th><!--Button Column--></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${badges}" var="badge">
                        <tr onclick="window.location = '/pa165/badge/detail/${badge.id}'" style="cursor: pointer;">
                            <td><c:out value="${badge.trainer.name} ${badge.trainer.surname}"/></td>
                            <td><javatime:format value="${badge.date}"/></td>
                            <td><fmt:message bundle="${s}" key="${badge.status}"/></td>
                            <td>
                                <c:if test="${gym.gymLeader.id eq userId}">
                                    <c:if test="${badge.status eq 'WAITING_TO_ACCEPT'}">
                                        <my:extraTag href="/badge/takeChallenge/${badge.id}?challengeWon=true" class='btn btn-primary'>
                                            <span class="glyphicon glyphicon-ok"></span>
                                            <fmt:message key="badge.accept"/>
                                        </my:extraTag>
                                        <my:extraTag href="/badge/takeChallenge/${badge.id}?challengeWon=false" class='btn btn-primary'>
                                            <span class="glyphicon glyphicon-ban-circle"></span>
                                            <fmt:message key="badge.deny"/>
                                        </my:extraTag>
                                    </c:if>
                                    <c:if test="${badge.status eq 'WON'}">
                                        <my:extraTag href="/badge/revoke/${badge.id}" class='btn btn-primary'>
                                            <span class="glyphicon glyphicon-minus"></span>
                                            <fmt:message key="badge.revoke"/>
                                        </my:extraTag>
                                    </c:if>
                                    <c:if test="${badge.status eq 'REVOKED'}">
                                        <my:extraTag href="/badge/reissue/${badge.id}" class='btn btn-primary'>
                                            <span class="glyphicon glyphicon-plus"></span>
                                            <fmt:message key="badge.reissue"/>
                                        </my:extraTag>
                                    </c:if>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

    </jsp:attribute>
</my:pagetemplate>

