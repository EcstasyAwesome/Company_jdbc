<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Профиль пользователя</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/stylesheet/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/static/top.jsp"/>
<main>
    <table align="center">
        <tr>
            <td rowspan="8" width="250" align="center">
                <img src="${pageContext.request.contextPath}${sessionUser.avatar}" width="200">
            </td>
            <td width="110">Логин:</td>
            <td>${sessionUser.login}</td>
        </tr>
        <tr>
            <td>Фамилия:</td>
            <td>${sessionUser.surname}</td>
        </tr>
        <tr>
            <td>Имя:</td>
            <td>${sessionUser.firstName}</td>
        </tr>
        <tr>
            <td>Отчество:</td>
            <td>${sessionUser.middleName}</td>
        </tr>
        <tr>
            <td>Телефон:</td>
            <td>${sessionUser.phone}</td>
        </tr>
        <tr>
            <td>Должность:</td>
            <td>${sessionUser.position.name}</td>
        </tr>
        <tr>
            <td>Регистрация:</td>
            <td><fmt:formatDate value="${sessionUser.registerDate}" pattern="dd.MM.yyyy"/></td>
        </tr>
        <tr>
            <td>Группа:</td>
            <td>${sessionUser.group.name}</td>
        </tr>
    </table>
</main>
<jsp:include page="/WEB-INF/jsp/static/bottom.jsp"/>
</body>
</html>