<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Добавить пользователя</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/stylesheet/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/static/top.jsp"/>
<main>
    <article>
        <form id="add" method="post">
            <input type="hidden" name="method" value="PUT">
            <input type="hidden" name="form" value="addUser">
            <h4>Заполните форму<br>
                <small>Все поля обязательны к заполнению!</small>
            </h4>
            <table>
                <tr>
                    <td class="add-table">Логин:</td>
                    <td class="add-table"><input type="text" name="user_login" placeholder=" Придумайте логин"
                                                 size="20" required autofocus></td>
                </tr>
                <tr>
                    <td class="add-table">Пароль:</td>
                    <td class="add-table"><input type="password" name="user_password" placeholder=" Придумайте пароль"
                                                 size="20" required></td>
                </tr>
                <tr>
                    <td class="add-table">Фамилия:</td>
                    <td class="add-table"><input type="text" name="user_surname" placeholder=" Введите фамилию"
                                                 size="20" required></td>
                </tr>
                <tr>
                    <td class="add-table">Имя:</td>
                    <td class="add-table"><input type="text" name="user_firstName" placeholder=" Введите имя" size="20"
                                                 required></td>
                </tr>
                <tr>
                    <td class="add-table">Отчество:</td>
                    <td class="add-table"><input type="text" name="user_secondName" placeholder=" Введите отчество"
                                                 size="20" required></td>
                </tr>
                <tr>
                    <td class="add-table">Телефон:</td>
                    <td class="add-table"><input type="text" name="user_phoneNumber"
                                                 placeholder=" Введите номер телефона" size="20" required>
                    </td>
                </tr>
                <tr>
                    <td class="add-table">Должность:</td>
                    <td class="add-table">
                        <select style="width: 100%" form="add" name="position_id" required>
                            <option value="0" selected disabled>Выберите должность</option>
                            <c:if test="${positions!=null}">
                                <c:forEach items="${positions}" var="position">
                                    <option value="${position.getId()}">${position.getName()}</option>
                                </c:forEach>
                            </c:if>
                            <c:if test="${positions.isEmpty()}">
                                <option disabled>Список пуст</option>
                            </c:if>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="add-table">Статус:</td>
                    <td class="add-table">
                        <input type="radio" value="true" name="user_isAdmin" required>Администратор
                    </td>
                </tr>
                <tr>
                    <td class="add-table" colspan="2">
                        <input type="radio" value="false" name="user_isAdmin" checked required>Пользователь
                    </td>
                </tr>
                <tr>
                    <td class="add-table" colspan="2"><input type="submit" value="Добавить"></td>
                </tr>
            </table>
        </form>
    </article>
    <aside>
        <h4>Меню:</h4>
        <menu>
            <li><a href="${pageContext.request.contextPath}/users">Список/Поиск</a></li>
            <li><a href="${pageContext.request.contextPath}/users/add">Добавить</a></li>
        </menu>
    </aside>
</main>
<jsp:include page="/WEB-INF/jsp/static/bottom.jsp"/>
</body>
</html>