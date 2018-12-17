<%--
    Document   : Jsp page fog detailed information about a specific trainer.
    Author     : Michal Mokros 456442
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
<fmt:setBundle basename="Texts"/>
<fmt:message var="title" key="trainer"/>
<my:pagetemplate title="${title}">
    <jsp:attribute name="body">
        <h1><fmt:message key="trainer"/> ${trainer.userName}</h1>
        <div class="table-responsive">
            <table class="table table-hover">
                <tbody>
                    <tr>
                        <th><fmt:message key="trainer.username"/></th>
                        <td><c:out value="${trainer.userName}"/></td>
                    </tr>

                    <tr>
                        <th><fmt:message key="trainer.name"/></th>
                        <td><c:out value="${trainer.name}"/></td>
                    </tr>
                    <tr>
                        <th><fmt:message key="trainer.surname"/></th>
                        <td><c:out value="${trainer.surname}"/></td>
                    </tr>
                    <tr>
                        <th><fmt:message key="trainer.born"/></th>
                        <td><javatime:format value="${trainer.born}" /></td>
                    </tr>
                    <tr>
                        <th><fmt:message key="trainer.admin"/></th>
                        <td><c:out value="${trainer.admin}"/></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <h3><fmt:message key="trainer.pokemons"/></h3>
        <div class="table-responsive">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th><fmt:message key="trainer.pokemons.nickname"/></th>
                        <th><fmt:message key="trainer.pokemons.species"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${pokemons}" var="pokemon">
                        <tr onclick="window.location = '/pa165/pokemon/detail/${pokemon.id}'" style="cursor: pointer;">
                            <td><c:out value="${pokemon.nickname}"/></td>
                            <td><c:out value="${pokemon.species.speciesName}"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <my:extraTag href="/badge/list?trainerId=${trainer.id}" class="btn btn-default">
            <fmt:message key="trainer.badge"/>
        </my:extraTag>
    </jsp:attribute>
</my:pagetemplate>

