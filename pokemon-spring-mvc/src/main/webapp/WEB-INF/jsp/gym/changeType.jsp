<%--
    Document   : Jsp page for changing type of a gym.
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
<fmt:setBundle basename="Types" var = "t"/>
<fmt:message var="title" key="gym.at.location"><fmt:param value="${gymToUpdate.location}"/></fmt:message>
<my:pagetemplate title="${title}">
    <jsp:attribute name="body">
        <h1><fmt:message key="gym.change.type"/></h1>

        <form:form method="post" action="${pageContext.request.contextPath}/gym/changeType/${gymToUpdate.id}"
                   modelAttribute="gymToUpdate" cssClass="form-horizontal">

            <div class="form-group">
                <form:label path="type" cssClass="col-sm-2 control-label">
                    <fmt:message key="gym.type"/>
                </form:label>
                <div class="col-sm-10">
                    <form:select path="type" cssClass="form-control">
                        <c:forEach items="${allTypes}" var="type">
                            <form:option value="${type}"><fmt:message bundle="${t}" key="${type}"/></form:option>
                        </c:forEach>
                        <form:option value="${type}"><c:out value="${none}"/></form:option>
                    </form:select>
                    <form:errors path="type" cssClass="error"/>
                </div>
            </div>

            <button type="submit" class="btn btn-primary" style="margin-top: 10px;">
                <span class="glyphicon glyphicon-edit"></span>
                <fmt:message key="confirm"/>
            </button>
        </form:form>

    </jsp:attribute>
</my:pagetemplate>