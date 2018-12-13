<%-- 
    Document   : Jsp page for listing all pokemons.
    Author     : Jiří Medveď 38451
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false"
         session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<fmt:setLocale value="${pageContext.request.locale}"/>
<fmt:setBundle basename="Texts"/>
<fmt:message var="title" key="pokemon"/>
<my:pagetemplate title="${title}">
    <jsp:attribute name="body">
        <h1><fmt:message key="pokemon.list.title">
                <fmt:param value="${principal.name}"/>
                <fmt:param value="${principal.surname}"/>
            </fmt:message>
        </h1>
        <table class="table">
            <thead>
                <tr>
                    <th><fmt:message key="pokemon.nickname"/></th>
                    <th><fmt:message key="pokemon.species.species.name"/></th>
                    <th><fmt:message key="pokemon.level"/></th>
                    <th><fmt:message key="pokemon.date.time.of.capture"/></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${pokemons}" var="pokemon">

                    <tr onclick="window.location = '/pa165/pokemon/detail/${pokemon.id}'" style="cursor: pointer;">
                        <td><c:out value="${pokemon.nickname}"/></td>
                        <td>
                            <my:extraTag href="/pokemonSpecies/detail/${pokemon.species.id}" class='btn btn-primary'>
                                <span class="glyphicon glyphicon-align-justify"></span>
                            </my:extraTag>
                            <c:out value="${pokemon.species.speciesName}"/>
                        </td>
                        <td><c:out value="${pokemon.level}"/></td>
                        <td>
                            <fmt:parseDate value="${pokemon.dateTimeOfCapture}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                            <fmt:formatDate value="${parsedDateTime}" type="both"  dateStyle="MEDIUM" timeStyle="SHORT"/>
                        </td>
                        <c:set var="MaxLevel" value="${100}"/>
                        <td>
                            <my:extraTag href="/pokemon/evolve/${pokemon.id}" class='btn btn-primary'>
                                <span class="glyphicon glyphicon-edit"></span> 
                                <fmt:message key="pokemon.evolve"/>
                            </my:extraTag>
                        </td>
                        <td>
                            <my:extraTag href="/pokemon/gift/${pokemon.id}" class='btn btn-primary'>
                                <span class="glyphicon glyphicon-edit"></span> 
                                <fmt:message key="pokemon.gift"/>
                            </my:extraTag>
                        </td>
                        <c:if test="${pokemon.level < MaxLevel}">
                            <td>
                                <my:extraTag href="/pokemon/levelup/${pokemon.id}" class='btn btn-primary'>
                                    <span class="glyphicon glyphicon-edit"></span> 
                                    <fmt:message key="pokemon.level.up"/>
                                </my:extraTag>
                            </td>
                        </c:if>

                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </jsp:attribute> 
</my:pagetemplate>

