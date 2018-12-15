<%--
    Document   : Jsp page for changing leader of a gym.
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
        <h1><fmt:message key="gym.edit.admin"/></h1>

        <form:form method="post" action="${pageContext.request.contextPath}/admin/gym/changeLeader/${gymToUpdate.id}"
                   modelAttribute="gymToUpdate" cssClass="form-horizontal">

            <div class="form-group">
                <form:label path="type" cssClass="col-sm-2 control-label">
                    <fmt:message key="gym.leader"/>
                </form:label>
                <div class="col-sm-10">
                    <form:select path="gymLeader" cssClass="form-control">
                        <c:forEach items="${possibleTrainers}" var="trainer">
                            <form:option value="${trainer.id}"><c:out value="${trainer.name} ${trainer.surname}"/></form:option>
                        </c:forEach>
                    </form:select>
                    <form:errors path="gymLeader" cssClass="error"/>
                </div>
            </div>

            <button type="submit" class="btn btn-primary" style="margin-top: 10px;">
                <span class="glyphicon glyphicon-edit"></span>
                <fmt:message key="confirm"/>
            </button>
        </form:form>

    </jsp:attribute>
</my:pagetemplate>