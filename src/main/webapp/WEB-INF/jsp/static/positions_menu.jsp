<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.github.company.servlet.positions.PositionSearch" %>
<%@ page import="com.github.company.servlet.positions.PositionCreate" %>
<h4>Меню:</h4>
<menu>
    <li><a href="${pageContext.request.contextPath}${PositionSearch.MAIN}">Список</a></li>
    <li><a href="${pageContext.request.contextPath}${PositionCreate.ADD}">Добавить</a></li>
</menu>