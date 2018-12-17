<%-- 
    Document   : Jsp page fog detailed information about pokemons.
    Author     : Jiří Medveď 38451
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false"
         session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
<fmt:setBundle basename="Texts"/>
<fmt:message var="title" key="pokemon"/>
<my:pagetemplate title="${title}">
    <jsp:attribute name="body">
        <h1><fmt:message key="pokemon"/> ${pokemon.nickname}</h1>
        <div class="table-responsive">
            <table class="table table-hover">
                <tbody>
                    <tr>
                        <th><fmt:message key="pokemon.level"/></th>
                        <td><c:out value="${pokemon.level}"/></td>
                    </tr>
                    <tr onclick="window.location = '/pa165/pokemonSpecies/detail/${pokemon.species.id}'" style="cursor: pointer;">
                        <th><fmt:message key="pokemon.species"/></th>
                        <td><c:out value="${pokemon.species.speciesName}"/></td>
                    </tr>
                    <tr>
                        <th><fmt:message key="pokemon.date.time.of.capture"/></th>
                        <td> 
                            <javatime:format value="${pokemon.dateTimeOfCapture}" style="MS"/>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <my:extraTag href="/pokemon/list" class="btn btn-primary">
            <fmt:message key="all.my.pokemons"/>
        </my:extraTag>
    </jsp:attribute>
</my:pagetemplate>
