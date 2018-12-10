<%-- 
    Document   : Jsp page for list all pokemon species.
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
<fmt:message var="title" key="pokemon.species"/>
<my:pagetemplate title="${title}">
<jsp:attribute name="body">
    <h1><fmt:message key="pokemon.species"/> ${pokemonSpecies.speciesName}</h1>
    <table class="table">
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
            <tr onclick="window.location='/pa165/pokemonSpecies/detail/${pokemonSpecies.id}'" style="cursor: pointer;">
                <td><c:out value="${pokemonSpecies.speciesName}"/></td>
                <td><c:out value="${pokemonSpecies.primaryType}"/></td>
                <td><c:out value="${pokemonSpecies.secondaryType}"/></td>
                <td><c:out value="${pokemonSpecies.evolvesFrom}"/></td>
                <td>
                    <my:extraTag href="/pokemonSpecies/changeTyping/${pokemonSpecies.id}" class='btn btn-primary'>
                        <span class="glyphicon glyphicon-edit"></span> 
                        <fmt:message key="pokemon.species.change.typing"/>
                    </my:extraTag>
                </td>
                <td>
                    <my:extraTag href="/pokemonSpecies/changePreevolution/${pokemonSpecies.id}" class='btn btn-primary'>
                        <span class="glyphicon glyphicon-edit"></span> 
                        <fmt:message key="pokemon.species.change.preevolution"/>
                    </my:extraTag>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <my:extraTag href="/pokemonSpecies/new" class="btn btn-default">
        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
            <fmt:message key="pokemon.species.create.new"/>
    </my:extraTag>
        
</jsp:attribute> 
</my:pagetemplate>

