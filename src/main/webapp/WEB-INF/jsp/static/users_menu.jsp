<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.github.company.servlet.users.UserSearch" %>
<%@ page import="com.github.company.servlet.users.UserCreate" %>
<h4>Меню:</h4>
<menu>
    <li><a href="${pageContext.request.contextPath}${UserSearch.MAIN}">Список</a></li>
    <li><a href="${pageContext.request.contextPath}${UserCreate.ADD}">Добавить</a></li>
</menu>