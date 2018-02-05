<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Профиль пользователя</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/stylesheet/style.css">
</head>
<body>
<header>
    <img src="${pageContext.request.contextPath}/resources/img/ecstasy_logo.jpg" alt="Логотип" height="200" width="200">
    <h1>Профиль пользователя</h1>
</header>
<nav>
    <form id="LOGOUT" action="/authorization" method="post">
        <input type="hidden" name="method" value="LOGOUT">
    </form>
    <table class="nav-menu">
        <tr>
            <td class="nav-menu-left">
                <a href="/">Главная</a> |
                <a href="/positions">Должностя</a> |
                <a href="/users">Пользователи</a> |
                <a href="/about">О нас</a>
            </td>
            <td class="nav-menu-right">
                <a href="/profile">Профиль</a> |
                <a href="/edit">Редактировать</a> |
                <input type="submit" form="LOGOUT" class="logout" value="Выход">
            </td>
        </tr>
    </table>
</nav>
<main>
    <div align="center">
        <p>Логин: ${sessionUser.getUserLogin()}</p>
        <p>Фамилия: ${sessionUser.getUserSurname()}</p>
        <p>Имя: ${sessionUser.getUserFirstName()}</p>
        <p>Отчество: ${sessionUser.getUserSecondName()}</p>
        <p>Телефон: ${sessionUser.getUserPhoneNumber()}</p>
        <p>Должность: ${sessionUser.getPositionByPositionId().getPositionName()}</p>
        <p>Дата регистрации: ${sessionUser.getUserRegisterDate()}</p>
        <p>Статус: <c:if test="${sessionUser.getUserIsAdmin() eq true}">Администратор</c:if>
            <c:if test="${sessionUser.getUserIsAdmin() eq false}">Пользователь</c:if></p>
    </div>
</main>
<footer>
    <address>
        <a href="mailto:ecstasy.awesome@gmail.com">Написать письмо</a>
    </address>
    <p>
        <small>Ecstasy © 2017</small>
    </p>
</footer>
</body>
</html>