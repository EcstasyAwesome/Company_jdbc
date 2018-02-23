<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.company.servlet.Users" %>
<h4>Меню:</h4>
<menu>
    <li><a href="${pageContext.request.contextPath}${Users.MAIN}">Список</a></li>
    <li><a href="${pageContext.request.contextPath}${Users.ADD}">Добавить</a></li>
</menu>