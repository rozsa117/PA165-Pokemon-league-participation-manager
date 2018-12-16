<%--
    Document   : Jsp page for change typing of pokemon species.
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
        <h1><fmt:message key="pokemon.species.change.typing"/></h1>

        <form:form method="post" action="${pageContext.request.contextPath}/admin/pokemonSpecies/changeTyping/${pokemonSpeciesToUpdate.id}"
                   modelAttribute="pokemonSpeciesToUpdate" cssClass="form-horizontal">
            <div class="form-group">
                <form:label path="primaryType" cssClass="col-sm-2 control-label">
                    <fmt:message key="pokemon.species.primary.type"/>
                </form:label>
                <div class="col-sm-10">
                    <form:select path="primaryType" cssClass="form-control">
                        <c:forEach items="${allTypes}" var="types">
                            <form:option value="${types}"><fmt:message bundle="${t}" key="${types}"/></form:option>
                        </c:forEach>
                    </form:select>
                    <form:errors path="primaryType" cssClass="error"/>
                </div>
            </div>

            <div class="form-group">
                <form:label path="secondaryType" cssClass="col-sm-2 control-label">
                    <fmt:message key="pokemon.species.secondary.type"/>
                </form:label>
                <div class="col-sm-10">
                    <form:select path="secondaryType" cssClass="form-control">
                        <c:forEach items="${allTypes}" var="types">
                            <form:option value="${types}"><fmt:message bundle="${t}" key="${types}"/></form:option>
                        </c:forEach>
                        <form:option value="${types}"><c:out value="${none}"/></form:option>
                    </form:select>
                    <form:errors path="secondaryType" cssClass="error"/>
                </div>
            </div>

            <button type="submit" class="btn btn-primary" style="margin-top: 10px;">
                <span class="glyphicon glyphicon-edit"></span>
                <fmt:message key="confirm"/>
            </button>
        </form:form>

    </jsp:attribute>
</my:pagetemplate>