<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Изменить должность</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/stylesheet/style.css">
</head>
<body>
<header>
    <img src="${pageContext.request.contextPath}/resources/img/ecstasy_logo.jpg" alt="Логотип" height="200" width="200">
    <h1>Должностя</h1>
    <p>Изменить существующую должность</p>
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
        <form id="update" method="post">
            <input type="hidden" name="method" value="UPDATE">
            <input type="hidden" name="position_id" value="${position.getPositionId()}">
            <p>* - поля, доступные для изменения</p>
            <table align="center">
                <tr>
                    <th width="40" class="table-top">ID</th>
                    <th width="200" class="table-top">Должность*</th>
                    <th width="400" class="table-top">Доп. информация*</th>
                </tr>
                <c:if test="${position!=null}">
                    <tr>
                        <td class="table-main">${position.getPositionId()}</td>
                        <td class="table-main">
                            <input class="edit-input" value="${position.getPositionName()}" name="position_name"
                                   autofocus
                                   required>
                        </td>
                        <td class="table-main">
                            <input class="edit-input" type="text" value="${position.getPositionDescription()}"
                                   name="position_description" required>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3" align="center">
                            <br><input type="submit" value="Сохранить изменения">
                        </td>
                    </tr>
                </c:if>
                <c:if test="${position==null}">
                    <tr>
                        <td class="table-main" colspan="3">Запись уже не существует</td>
                    </tr>
                </c:if>
            </table>
        </form>
    </article>
    <aside>
        <h4>Меню:</h4>
        <menu>
            <li><a href="/positions">Список</a></li>
            <li><a href="/positions/add">Добавить</a></li>
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