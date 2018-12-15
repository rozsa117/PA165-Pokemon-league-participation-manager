<%--
    Document   : Jsp page for listing all pokemon species.
    Author     : Tamás Rózsa 445653
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false"
         session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<fmt:setBundle basename="Texts"/>
<fmt:setBundle basename="Types" var = "t"/>
<fmt:message var="title" key="pokemon.species"/>
<my:pagetemplate title="${title}">
    <jsp:attribute name="body">
        <h1><fmt:message key="pokemon.species"/> ${pokemonSpecies.speciesName}</h1>
        <div class="table-responsive">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th><fmt:message key="pokemon.species.species.name"/></th>
                        <th><fmt:message key="pokemon.species.primary.type"/></th>
                        <th><fmt:message key="pokemon.species.secondary.type"/></th>
                        <th><fmt:message key="pokemon.species.evolves.from"/></th>
                        <th><!--Button Column--></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${allPokemonSpecies}" var="pokemonSpecies">
                        <tr onclick="window.location = '/pa165/pokemonSpecies/detail/${pokemonSpecies.id}'" style="cursor: pointer;">
                            <td><c:out value="${pokemonSpecies.speciesName}"/></td>
                            <td><fmt:message bundle="${t}" key="${pokemonSpecies.primaryType}"/></td>
                            <td><fmt:message bundle="${t}" key="${empty pokemonSpecies.secondaryType ? 'empty' : pokemonSpecies.secondaryType }"/></td>
                            <td><c:out value="${pokemonSpecies.evolvesFrom.speciesName}"/></td>
                            <security:authorize access="hasRole('ADMIN')">
                                <td>
                                    <my:extraTag href="/admin/pokemonSpecies/changeTyping/${pokemonSpecies.id}" class='btn btn-primary'>
                                        <span class="glyphicon glyphicon-edit"></span>
                                        <fmt:message key="pokemon.species.change.typing"/>
                                    </my:extraTag>
                                    <my:extraTag href="/admin/pokemonSpecies/changePreevolution/${pokemonSpecies.id}" class='btn btn-primary'>
                                        <span class="glyphicon glyphicon-edit"></span>
                                        <fmt:message key="pokemon.species.change.preevolution"/>
                                    </my:extraTag>
                                </td>
                            </security:authorize>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <security:authorize access="hasRole('ADMIN')">
            <my:extraTag href="/admin/pokemonSpecies/create" class="btn btn-primary">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                <fmt:message key="pokemon.species.create.new"/>
            </my:extraTag>
        </security:authorize>

    </jsp:attribute>
</my:pagetemplate>

