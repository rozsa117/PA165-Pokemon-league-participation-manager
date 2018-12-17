<%--
    Document   : Jsp page for creation of new trainer.
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
    <h1><fmt:message key="trainer.create.new"/></h1>

    <form:form method="post" action="${pageContext.request.contextPath}/admin/trainer/new"
               modelAttribute="trainerCreate" cssClass="form-horizontal">

        <div class="form-group ${userName_error?'has-error':''}">
            <form:label path="userName" cssClass="col-sm-2 control-label">
                <fmt:message key="trainer.username"/>
            </form:label>
            <div class="col-sm-10">
                <form:input path="userName" cssClass="form-control"/>
                <form:errors path="userName" cssClass="help-block"/>
            </div>
        </div>

        <div class="form-group ${password_error?'has-error':''}">
            <form:label path="password" cssClass="col-sm-2 control-label">
                <fmt:message key="trainer.password"/>
            </form:label>
            <div class="col-sm-10">
                <form:password path="password" cssClass="form-control"/>
                <form:errors path="password" cssClass="help-block"/>
            </div>
        </div>

        <div class="form-group ${name_error?'has-error':''}">
            <form:label path="name" cssClass="col-sm-2 control-label">
                <fmt:message key="trainer.name"/>
            </form:label>
            <div class="col-sm-10">
                <form:input path="name" cssClass="form-control"/>
                <form:errors path="name" cssClass="help-block"/>
            </div>
        </div>

        <div class="form-group ${surname_error?'has-error':''}">
            <form:label path="surname" cssClass="col-sm-2 control-label">
                <fmt:message key="trainer.surname"/>
            </form:label>
            <div class="col-sm-10">
                <form:input path="surname" cssClass="form-control"/>
                <form:errors path="surname" cssClass="help-block"/>
            </div>
        </div>

        <div class="form-group ${born_error?'has-error':''}">
            <form:label path="born" cssClass="col-sm-2 control-label">
                <fmt:message key="trainer.born"/>
            </form:label>
                <div class="col-sm-10">
                    <form:input path="born" type="date" class="form-control"/>
                    <form:errors path="born" cssClass="help-block"/>
                </div>
        </div>

        <div class="form-group">
            <form:label path="admin" cssClass="col-sm-2 control-label">
                <fmt:message key="trainer.admin"/>
            </form:label>
            <div class="col-sm-10">
                <form:select path="admin" cssClass="form-control">
                    <form:option value="true"><fmt:message key="trainer.admin.true"/></form:option>
                    <form:option value="false"><fmt:message key="trainer.admin.false"/></form:option>
                </form:select>
                <form:errors path="admin" cssClass="error"/>
            </div>
        </div>

        <button type="submit" class="btn btn-primary" style="margin-top: 10px;">
            <span class="glyphicon glyphicon-edit"></span>
            <fmt:message key="confirm"/>
        </button>
    </form:form>

</jsp:attribute>
</my:pagetemplate>