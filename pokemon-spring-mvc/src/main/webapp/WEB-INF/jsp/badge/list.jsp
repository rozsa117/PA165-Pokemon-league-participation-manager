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
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
<fmt:setLocale value="${pageContext.request.locale}"/>
<fmt:setBundle basename="Texts"/>
<fmt:setBundle basename="ChallengeStatus" var = "s"/>
<fmt:message var="title" key="badges"/>
<my:pagetemplate title="${title}">
    <jsp:attribute name="body">
        <h1>
            <fmt:message key="badges.of.entity">
                <fmt:param value="${trainer.name} ${trainer.surname}"/>
            </fmt:message>
        </h1>
        <c:set var="userId"><security:authentication property="principal.trainerId"/></c:set>
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th><fmt:message key="badge.gym"/></th>
                        <th><fmt:message key="badge.first.challenged.on"/></th>
                        <th><fmt:message key="badge.status"/></th>
                        <th><!--Button Column--></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${badges}" var="badge">

                        <tr onclick="window.location = '/pa165/badge/detail/${badge.id}'" style="cursor: pointer;">
                            <td><fmt:message key="gym.at.location"><fmt:param value="${badge.gym.location}"/></fmt:message></td>
                            <td><javatime:format value="${badge.date}"/></td>
                            <td><fmt:message bundle="${s}" key="${badge.status}"/></td>
                            <td>
                                <c:if test="${badge.trainer.id eq userId && badge.status eq 'LOST'}">
                                    <my:extraTag href="/badge/rechallenge/${badge.id}" class='btn btn-primary'>
                                        <span class="glyphicon glyphicon-eye-open"></span>
                                        <fmt:message key="badge.rechallenge"/>
                                    </my:extraTag>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

    </jsp:attribute>
</my:pagetemplate>

