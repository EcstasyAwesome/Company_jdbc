<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Редактирование профиля</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/stylesheet/style.css">
</head>
<body>
<header>
    <img src="${pageContext.request.contextPath}/resources/img/ecstasy_logo.jpg" alt="Логотип" height="200" width="200">
    <h1>Редактирование профиля</h1>
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
    <form id="update" method="post">
        <input type="hidden" name="method" value="PUT">
        <input type="hidden" name="form" value="updateUser">
        <table align="center">
            <tr>
                <td class="update-table">Фамилия</td>
                <td class="update-table">Имя</td>
                <td class="update-table">Отчество</td>
                <td class="update-table">Телефон</td>
                <td class="update-table">Пароль</td>
            </tr>
            <tr>
                <td class="update-table"><input type="text" size="15" value="${sessionUser.getSurname()}"
                                                name="user_surname" required></td>
                <td class="update-table"><input type="text" size="15" value="${sessionUser.getFirstName()}"
                                                name="user_firstName" required></td>
                <td class="update-table"><input type="text" size="15" value="${sessionUser.getSecondName()}"
                                                name="user_secondName" required></td>
                <td class="update-table"><input type="text" size="15" value="${sessionUser.getPhoneNumber()}"
                                                name="user_phoneNumber" required></td>
                <td class="update-table"><input type="text" size="15" value="${sessionUser.getPassword()}"
                                                name="user_password" required></td>
            </tr>
        </table>
        <input type="hidden" name="user_id" value="${sessionUser.getId()}">
        <input type="hidden" name="position_id" value="${sessionUser.getPosition()}">
        <input type="hidden" name="user_id" value="${sessionUser.isAdmin()}">
        <p align="center"><input type="submit" value="Сохранить изменения"></p>
    </form>
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