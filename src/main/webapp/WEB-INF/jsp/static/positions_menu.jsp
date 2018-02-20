<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.company.servlet.Positions" %>
<h4>Меню:</h4>
<menu>
    <li><a href="${pageContext.request.contextPath}${Positions.MAIN}">Список</a></li>
    <li><a href="${pageContext.request.contextPath}${Positions.ADD}">Добавить</a></li>
</menu>