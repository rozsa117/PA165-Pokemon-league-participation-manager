<%-- 
    Document   : Jsp page for creating new pokemon.
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
        <h1><fmt:message key="pokemon.create.new"/></h1>

        <form:form method="post" action="${pageContext.request.contextPath}/pokemon/create"
                   modelAttribute="pokemonCreate" cssClass="form-horizontal">

            <div class="form-group ${nickname_error?'has-error':''}">
                <form:label path="nickname" cssClass="col-sm-2 control-label">
                    <fmt:message key="pokemon.nickname"/>
                </form:label>
                <div class="col-sm-10">
                    <form:input path="nickname" cssClass="form-control"/>
                    <form:errors path="nickname" cssClass="help-block"/>
                </div>
            </div>

            <div class="form-group ${pokemonSpeciesId_error?'has-error':''}" >
                <form:label path="pokemonSpeciesId" cssClass="col-sm-2 control-label">
                    <fmt:message key="pokemon.species.singular"/>
                </form:label>
                <div class="col-sm-2">
                    <form:select path="pokemonSpeciesId" cssClass="form-control">
                        <c:forEach items="${allSpecies}" var="species">
                            <form:option value="${species.id}"><c:out value="${species.speciesName}"/></form:option>
                        </c:forEach>
                    </form:select>
                    <form:errors path="pokemonSpeciesId" cssClass="error"/>
                </div>
            </div>

            <div class="form-group ${level_error?'has-error':''}">
                <form:label path="level" cssClass="col-sm-2 control-label">
                    <fmt:message key="pokemon.level"/> 
                </form:label>
                <div class="col-sm-1">
                    <form:input path="level" type="number" min="1" max="100" cssClass="form-control"/>
                    <form:errors path="level" cssClass="help-block"/>
                </div>
            </div>

            <button type="submit" class="btn btn-primary" style="margin-top: 10px;">
                <span class="glyphicon glyphicon-edit"></span> 
                <fmt:message key="confirm"/>
            </button>
        </form:form>

    </jsp:attribute> 
</my:pagetemplate>