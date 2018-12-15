<%--
    Document   : Jsp page for detailed information about a specific badge.
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
<fmt:setBundle basename="Texts"/>
<fmt:setBundle basename="ChallengeStatus" var = "s"/>
<fmt:message var="title" key="badge"/>
<my:pagetemplate title="${title}">
    <jsp:attribute name="body">
        <h1><fmt:message key="badge"/></h1>
        <div class="table-responsive">
            <table class="table table-hover">
                <tbody>
                    <tr>
                        <th><fmt:message key="badge.trainer"/></th>
                        <td onclick="window.location = '/pa165/trainer/detail/${badge.trainer.id}'" style="cursor: pointer;">
                            <c:out value="${badge.trainer.name} ${badge.trainer.surname}"/>
                        </td>
                    </tr>

                    <tr onclick="window.location = '/pa165/gym/detail/${badge.gym.id}'" style="cursor: pointer;">
                        <th><fmt:message key="badge.gym"/></th>
                        <td><fmt:message key="gym.at.location"><fmt:param value="${badge.gym.location}"/></fmt:message></td>
                        </tr>
                        <tr>
                            <th><fmt:message key="badge.first.challenged.on"/></th>
                        <td><javatime:format value="${badge.date}"/></td>
                    </tr>
                    <tr>
                        <th><fmt:message key="badge.status"/></th>
                        <td><fmt:message bundle="${s}" key="${badge.status}"/></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <c:if test="${badge.trainer.id eq userId && badge.status eq 'LOST'}">
            <my:extraTag href="/badge/rechallenge/${badge.id}" class='btn btn-primary'>
                <span class="glyphicon glyphicon-eye-open"></span>
                <fmt:message key="badge.rechallenge"/>
            </my:extraTag>
        </c:if>
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
    </jsp:attribute>
</my:pagetemplate>
