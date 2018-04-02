<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<h4>Меню:</h4>
<menu>
    <li><a href="<c:url value="/users"/>">Список</a></li>
    <li><a href="<c:url value="/users/add"/>">Добавить</a></li>
</menu>