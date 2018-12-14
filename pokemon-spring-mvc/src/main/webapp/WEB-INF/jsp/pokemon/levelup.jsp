<%-- 
    Document   : Jsp page for level up pokemon.
    Author     : Jiří Medveď 38451
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false"
         session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<fmt:setBundle basename="Texts"/>
<fmt:message var="title" key="pokemon"/>
<my:pagetemplate title="${title}">
    <jsp:attribute name="body">
        <h1><fmt:message key="pokemon.level.up" /> ${pokemon.nickname}</h1>

        <form:form method="post" action="${pageContext.request.contextPath}/pokemon/levelup/${pokemonToLevelUp.pokemonId}"
                   modelAttribute="pokemonToLevelUp" cssClass="form-horizontal">

            <div class="form-group ${newLevel_error?'has-error':''}">
                <form:label path="newLevel" cssClass="col-sm-2 control-label">
                    <fmt:message key="pokemon.new.level"/>
                </form:label>
                <div class="col-sm-1">
                    <form:input path="newLevel" 
                                cssClass="form-control"             
                                type="number"
                                min="${pokemonToLevelUp.newLevel}"
                                max="100"/>
                    <form:errors path="newLevel" cssClass="error"/>
                </div>
            </div>

            <button type="submit" class="btn btn-primary" style="margin-top: 10px;">
                <span class="glyphicon glyphicon-edit"></span> 
                <fmt:message key="confirm"/>
            </button>
            <form:input path="requestingTrainerId" type="hidden"/>
        </form:form>
    </jsp:attribute> 
</my:pagetemplate>