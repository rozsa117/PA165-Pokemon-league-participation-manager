<%--
    Document   : Jsp page for confirming badge operation.
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
<fmt:message var="title" key="confirm"/>
<my:pagetemplate title="${title}">
    <jsp:attribute name="body">

        <p>
            <fmt:message key="${msgKey}">
                <fmt:param value="${subject}"/>
            </fmt:message>
        </p>

        <form:form method="post" action="${pageContext.request.contextPath}${postUrl}"
                   modelAttribute="requestParam" cssClass="form-horizontal">
            <c:if test="${not empty requestParam}">
                <input type="hidden" name="requestParam" value="${requestParam}"/>
            </c:if>

            <button type="submit" class="btn btn-primary" style="margin-top: 10px;">
                <span class="glyphicon glyphicon-ok"></span>
                <fmt:message key="confirm"/>
            </button>
        </form:form>

    </jsp:attribute>
</my:pagetemplate>