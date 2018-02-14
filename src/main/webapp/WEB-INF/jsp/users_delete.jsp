<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Удалить пользователя</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/stylesheet/style.css">
</head>
<body>
<header>
    <img src="${pageContext.request.contextPath}/resources/img/ecstasy_logo.jpg" alt="Логотип" height="200" width="200">
    <h1>Пользователи</h1>
    <p>Удалить существующего пользователя</p>
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
    <article>
        <br>
        <form method="post">
            <input type="hidden" name="method" value="DELETE">
            <input type="hidden" name="form" value="deleteUser">
            <select name="user_id" required>
                <option selected disabled>Выберите ID</option>
                <c:if test="${users!=null}">
                    <c:forEach items="${users}" var="user">
                        <option value="${user.getId()}">ID${user.getId()}
                            - ${user.getSurname()} ${user.getFirstName()} ${user.getSecondName()}</option>
                    </c:forEach>
                </c:if>
                <c:if test="${users.isEmpty()}">
                    <option disabled>Список пуст</option>
                </c:if>
            </select>
            <input type="submit" value="Удалить">
        </form>
    </article>
    <aside>
        <h4>Меню:</h4>
        <menu>
            <li><a href="/users">Список/Поиск</a></li>
            <li><a href="/users/add">Добавить</a></li>
        </menu>
    </aside>
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