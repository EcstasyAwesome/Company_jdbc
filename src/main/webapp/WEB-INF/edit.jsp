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
    <form id="avatar" method="post">
        <input type="hidden" name="method" value="DELETE">
    </form>
    <form method="post" enctype="multipart/form-data">
        <input type="hidden" name="method" value="UPDATE">
        <table align="center">
            <tr>
                <td rowspan="6" width="250" align="center">
                    <img src="${pageContext.request.contextPath}${sessionUser.getUserAvatar()}" width="200">
                </td>
                <td width="110">Фамилия:</td>
                <td><input type="text" size="15" value="${sessionUser.getUserSurname()}"
                           name="user_surname" required></td>
            </tr>
            <tr>
                <td>Имя:</td>
                <td><input type="text" size="15" value="${sessionUser.getUserFirstName()}"
                           name="user_firstName" required></td>
            </tr>
            <tr>
                <td>Отчество:</td>
                <td><input type="text" size="15" value="${sessionUser.getUserSecondName()}"
                           name="user_secondName" required></td>
            </tr>
            <tr>
                <td>Телефон:</td>
                <td><input type="text" size="15" value="${sessionUser.getUserPhoneNumber()}"
                           pattern="380[0-9]{9}" name="user_phoneNumber" required></td>
            </tr>
            <tr>
                <td>Пароль:</td>
                <td><input type="text" size="15" value="${sessionUser.getUserPassword()}"
                           name="user_password" required></td>
            </tr>
            <tr>
                <td>Фото:</td>
                <td><input type="file" name="user_avatar" accept="image/jpeg,image/png,image/gif">
                </td>
            </tr>
            <tr>
                <td align="center">
                    <c:if test="${sessionUser.getUserAvatar().endsWith('avatar.png')}">
                        <input form="avatar" type="submit" value="Удалить фото" disabled>
                    </c:if>
                    <c:if test="${!sessionUser.getUserAvatar().endsWith('avatar.png')}">
                        <input form="avatar" type="submit" value="Удалить фото" disabled>
                    </c:if>
                </td>
                <td colspan="2" align="center">
                    <small>Данное поле можно оставить пустым<br>
                        Максимальный размер файла 1024КБ
                    </small>
                    <c:if test="${profileError!=null}">
                        <p class="server-answer">${profileError}</p>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td colspan="3" align="center">
                    <br><input type="submit" value="Сохранить изменения">
                </td>
            </tr>
        </table>
    </form>
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