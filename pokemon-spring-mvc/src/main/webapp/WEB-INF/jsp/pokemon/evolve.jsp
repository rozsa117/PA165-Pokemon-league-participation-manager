<%-- 
    Document   : Jsp page for evolve pokemon.
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
        <h1><fmt:message key="pokemon.evolve" /> ${pokemon.nickname}</h1>

        <form:form method="post" action="${pageContext.request.contextPath}/pokemon/evolve/${pokemonToEvolve.id}"
                   modelAttribute="pokemonToEvolve" cssClass="form-horizontal">

            <div class="form-group ${newSpeciesId_error?'has-error':''}">
                <form:label path="newSpeciesId" cssClass="col-sm-2 control-label">
                    <fmt:message key="pokemon.species.singular"/>
                </form:label>
                <div class="col-sm-2">
                    <form:select path="newSpeciesId" cssClass="form-control">
                        <c:forEach items="${speciesEvolveTo}" var="species">
                            <form:option value="${species.id}"><c:out value="${species.speciesName}"/></form:option>
                        </c:forEach>
                    </form:select>
                    <form:errors path="newSpeciesId" cssClass="error"/>
                </div>
            </div>

            <button type="submit" class="btn btn-primary" style="margin-top: 10px;">
                <span class="glyphicon glyphicon-edit"></span> 
                <fmt:message key="confirm"/>
            </button>
        </form:form>
    </jsp:attribute> 
</my:pagetemplate>