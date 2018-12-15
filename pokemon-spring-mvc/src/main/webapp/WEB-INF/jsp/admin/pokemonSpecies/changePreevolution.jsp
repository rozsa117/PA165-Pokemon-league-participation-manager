<%--
    Document   : Jsp page for change preevolution of pokemon species.
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
<fmt:message var="title" key="pokemon.species.singular"/>
<my:pagetemplate title="${title}">
    <jsp:attribute name="body">
        <h1><fmt:message key="pokemon.species.change.preevolution"/></h1>

        <form:form method="post" action="${pageContext.request.contextPath}/admin/pokemonSpecies/changePreevolution/${pokemonSpeciesToUpdate.id}"
                   modelAttribute="pokemonSpeciesToUpdate" cssClass="form-horizontal">

            <div class="form-group">
                <form:label path="evolvesFrom" cssClass="col-sm-2 control-label">
                    <fmt:message key="pokemon.species.evolves.from"/>
                </form:label>
                <div class="col-sm-10">
                    <form:select path="evolvesFrom" cssClass="form-control">
                        <c:forEach items="${allSpecies}" var="species">
                            <form:option value="${species.id}"><c:out value="${species.speciesName}"/></form:option>
                        </c:forEach>
                        <form:option value="${species.id}"><c:out value="${none}"/></form:option>
                    </form:select>
                    <form:errors path="evolvesFrom" cssClass="error"/>
                </div>
            </div>

            <button type="submit" class="btn btn-primary" style="margin-top: 10px;">
                <span class="glyphicon glyphicon-edit"></span>
                <fmt:message key="confirm"/>
            </button>
        </form:form>

    </jsp:attribute>
</my:pagetemplate>