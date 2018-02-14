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
                <a href="/about">О компании</a>
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
    <br>
    <table align="center">
        <tr>
            <td rowspan="8" width="250" align="center">
                <img src="${pageContext.request.contextPath}${sessionUser.getUserAvatar()}" width="200">
            </td>
            <td width="110">Логин:</td>
            <td>${sessionUser.getUserLogin()}</td>
        </tr>
        <tr>
            <td>Фамилия:</td>
            <td>${sessionUser.getUserSurname()}</td>
        </tr>
        <tr>
            <td>Имя:</td>
            <td>${sessionUser.getUserFirstName()}</td>
        </tr>
        <tr>
            <td>Отчество:</td>
            <td>${sessionUser.getUserSecondName()}</td>
        </tr>
        <tr>
            <td>Телефон:</td>
            <td>${sessionUser.getUserPhoneNumber()}</td>
        </tr>
        <tr>
            <td>Должность:</td>
            <td>${sessionUser.getPositionByPositionId().getPositionName()}</td>
        </tr>
        <tr>
            <td>Регистрация:</td>
            <td>${sessionUser.getUserRegisterDate()}</td>
        </tr>
        <tr>
            <td>Статус:</td>
            <td>
                <c:if test="${sessionUser.getUserStatus()==2}">Администратор</c:if>
                <c:if test="${sessionUser.getUserStatus()==3}">Пользователь</c:if>
            </td>
        </tr>
    </table>
    <br>
</main>
<footer>
    <address>
        <a href="mailto:ecstasy.awesome@gmail.com">Написать письмо</a>
    </address>
    <p>
        <small>Ecstasy © 2018</small>
    </p>
</footer>
</body>
</html>