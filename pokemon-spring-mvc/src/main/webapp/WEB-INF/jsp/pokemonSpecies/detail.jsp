<%--
    Document   : Jsp page for detailed information about a specific pokemon species.
    Author     : Tamás Rózsa 445653
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
<fmt:message var="title" key="pokemon.species.singular"/>
<my:pagetemplate title="${title}">
    <jsp:attribute name="body">
        <h1><fmt:message key="pokemon.species.singular"/> ${pokemonSpecies.speciesName}</h1>
        <div class="table-responsive">
            <table class="table table-hover">
                <tbody>
                    <tr>
                        <th><fmt:message key="pokemon.species.species.name"/></th>
                        <td><c:out value="${pokemonSpecies.speciesName}"/></td>
                    </tr>

                    <tr>
                        <th><fmt:message key="pokemon.species.primary.type"/></th>
                        <td><fmt:message bundle="${t}" key="${pokemonSpecies.primaryType}"/></td>
                    </tr>
                    <tr>
                        <th><fmt:message key="pokemon.species.secondary.type"/></th>
                        <td><fmt:message bundle="${t}" key="${empty pokemonSpecies.secondaryType ? 'empty' : pokemonSpecies.secondaryType}"/></td>
                    </tr>
                    <tr>
                        <th><fmt:message key="pokemon.species.evolves.from"/></th>
                        <td><c:out value="${pokemonSpecies.evolvesFrom.speciesName}"/></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <my:extraTag href="/pokemonSpecies/list" class="btn btn-primary">
            <fmt:message key="all.pokemon.species"/>
        </my:extraTag>
    </jsp:attribute>
</my:pagetemplate>
