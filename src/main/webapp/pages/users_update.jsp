<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Изменить пользователя</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/stylesheet/style.css">
</head>
<body>
<header>
    <img src="${pageContext.request.contextPath}/resources/img/ecstasy_logo.jpg" alt="Логотип" height="200" width="200">
    <h1>Пользователи</h1>
    <p>Изменить существующего пользователя</p>
</header>
<nav>
    <form id="LOGOUT" method="post">
        <input type="hidden" name="method" value="LOGOUT">
    </form>
    <table class="nav-menu">
        <tr>
            <td id="nav-menu-left">
                <a href="/company">Главная</a> |
                <a href="/company/positions">Должностя</a> |
                <a href="/company/users">Пользователи</a> |
                <a href="/company/about">О нас</a>
            </td>
            <td id="nav-menu-right">
                <a href="/company/profile">Профиль</a> |
                <a href="/company/edit">Редактировать</a> |
                <input type="submit" form="LOGOUT" class="logout" value="Выход">
            </td>
        </tr>
    </table>
</nav>
<main>
    <article>
        <br>
        <c:if test="${button eq false}">
            <form>
                <input type="hidden" name="key" value="user_id">
                <select name="value" required>
                    <option selected disabled>Выберите ID</option>
                    <c:if test="${users!=null}">
                        <c:forEach items="${users}" var="user">
                            <option value="${user.getId()}">ID${user.getId()}
                                - ${user.getSurname()} ${user.getFirstName()} ${user.getSecondName()}</option>
                        </c:forEach>
                    </c:if>
                    <c:if test="${users.isEmpty()}">
                        <option disabled>список пуст</option>
                    </c:if>
                </select>
                <input type="submit" value="Редактировать">
            </form>
        </c:if>
        <c:if test="${button}">
            <form id="update" method="post">
                <input type="hidden" name="method" value="PUT">
                <input type="hidden" name="form" value="updateUser">
                <table>
                    <tr>
                        <td class="update-table">Фамилия</td>
                        <td class="update-table">Имя</td>
                        <td class="update-table">Отчество</td>
                        <td class="update-table">Телефон</td>
                        <td class="update-table">Должность</td>
                    </tr>
                    <tr>
                        <td class="update-table"><input type="text" size="15" value="${users.get(0).getSurname()}"
                                                        name="user_surname" required></td>
                        <td class="update-table"><input type="text" size="15" value="${users.get(0).getFirstName()}"
                                                        name="user_firstName" required></td>
                        <td class="update-table"><input type="text" size="15" value="${users.get(0).getSecondName()}"
                                                        name="user_secondName" required></td>
                        <td class="update-table"><input type="text" size="15" value="${users.get(0).getPhoneNumber()}"
                                                        name="user_phoneNumber" required></td>
                        <td class="update-table"><select form="update" name="position_id">
                            <c:if test="${positions!=null}">
                                <c:forEach items="${positions}" var="position">
                                    <c:if test="${users.get(0).getPosition()==position.getId()}">
                                        <option value="${position.getId()}" selected>${position.getName()}</option>
                                    </c:if>
                                    <c:if test="${users.get(0).getPosition()!=position.getId()}">
                                        <option value="${position.getId()}">${position.getName()}</option>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                            <c:if test="${positions.isEmpty()}">
                                <option value="${users.get(0).getPosition()}"
                                        selected>${users.get(0).getPosition()}</option>
                            </c:if>
                        </select></td>
                    </tr>
                    <tr>
                        <td class="update-table">Пароль</td>
                        <td class="update-table" colspan="2">Статус</td>
                    </tr>
                    <tr>
                        <td class="update-table"><input type="text" size="15" value="${users.get(0).getPassword()}"
                                                        name="user_password" required></td>
                        <td class="update-table"><input type="radio" value="true" name="user_isAdmin"
                                                        <c:if test="${users.get(0).isAdmin() eq true}">checked</c:if>
                                                        required>Администратор
                        </td>
                        <td class="update-table"><input type="radio" value="false" name="user_isAdmin"
                                                        <c:if test="${users.get(0).isAdmin() eq false}">checked</c:if>
                                                        required>Пользователь
                        </td>
                    </tr>
                </table>
                <input type="hidden" name="user_id" value="${users.get(0).getId()}">
                <p><input type="submit" value="Сохранить изменения"></p>
            </form>
        </c:if>
    </article>
    <aside>
        <h4>Меню:</h4>
        <menu>
            <li><a href="/company/users">Список/Поиск</a></li>
            <li><a href="/company/users/add">Добавить</a></li>
            <li><a href="/company/users/update">Изменить</a></li>
            <li><a href="/company/users/delete">Удалить</a></li>
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