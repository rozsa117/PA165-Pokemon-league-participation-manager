<%--
    Document   : Jsp page for password change of trainer.
    Author     : Michal Mokros 456442
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<fmt:setBundle basename="Texts"/>
<fmt:message var="title" key="trainer"/>
<my:pagetemplate title="${title}">
<jsp:attribute name="body">
    <h1><fmt:message key="trainer.changePassword"/></h1>

    <form:form method="post" action="${pageContext.request.contextPath}/trainer/changePassword/${trainerToUpdate.trainerId}"
               modelAttribute="trainerToUpdate" cssClass="form-horizontal">
        <div class="form-group">
            <form:label path="oldPassword" cssClass="col-sm-2 control-label">
                        <fmt:message key="trainer.oldPassword"/>
            </form:label>
            <div class="col-sm-10">
                <form:password path="oldPassword" cssClass="form-control"/>
                <form:errors path="oldPassword" cssClass="error"/>
            </div>
        </div>

        <div class="form-group">
            <form:label path="newPassword" cssClass="col-sm-2 control-label">
                        <fmt:message key="trainer.newPassword"/>
            </form:label>
            <div class="col-sm-10">
                <form:password path="newPassword" cssClass="form-control"/>
                <form:errors path="newPassword" cssClass="error"/>
            </div>
        </div>

        <button type="submit" class="btn btn-primary" style="margin-top: 10px;">
            <span class="glyphicon glyphicon-edit"></span>
            <fmt:message key="confirm"/>
        </button>
    </form:form>

</jsp:attribute>
</my:pagetemplate>
