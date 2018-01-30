<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Удалить должность</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/stylesheet/style.css">
</head>
<body>
<header>
    <img src="${pageContext.request.contextPath}/resources/img/ecstasy_logo.jpg" alt="Логотип" height="200" width="200">
    <h1>Должностя</h1>
    <p>Удалить существующую должность</p>
</header>
<nav>
    <form id="LOGOUT" action="/authorization" method="post">
        <input type="hidden" name="method" value="LOGOUT">
    </form>
    <table class="nav-menu">
        <tr>
            <td id="nav-menu-left">
                <a href="/">Главная</a> |
                <a href="/positions">Должностя</a> |
                <a href="/users">Пользователи</a> |
                <a href="/about">О нас</a>
            </td>
            <td id="nav-menu-right">
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
            <input type="hidden" name="form" value="deletePosition">
            <select name="position_id" required>
                <option selected disabled>Выберите ID</option>
                <c:if test="${positions!=null}">
                    <c:forEach items="${positions}" var="position">
                        <option value="${position.getId()}">ID${position.getId()} - ${position.getName()}</option>
                    </c:forEach>
                </c:if>
                <c:if test="${positions.isEmpty()}">
                    <option disabled>список пуст</option>
                </c:if>
            </select>
            <input type="submit" value="Удалить">
        </form>
    </article>
    <aside>
        <h4>Меню:</h4>
        <menu>
            <li><a href="/company/positions">Список</a></li>
            <li><a href="/company/positions/add">Добавить</a></li>
            <li><a href="/company/positions/update">Изменить</a></li>
            <li><a href="/company/positions/delete">Удалить</a></li>
        </menu>
    </aside>
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