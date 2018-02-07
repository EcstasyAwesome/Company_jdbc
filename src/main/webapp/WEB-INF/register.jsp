<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Регистрация</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/stylesheet/register.css">
</head>
<body>
<div class="page">
    <div class="form">
        <form id="add" method="post" enctype="multipart/form-data">
            <input type="hidden" name="method" value="REGISTER">
            <h2>Форма регистрации</h2>
            <p>* - поле можно оставить пустым</p>
            <table align="center">
                <tr>
                    <td class="add-table">Логин:</td>
                    <td class="add-table"><input type="text" name="user_login" placeholder="Придумайте логин"
                                                 size="20" required autofocus></td>
                </tr>
                <tr>
                    <td class="add-table">Пароль:</td>
                    <td class="add-table"><input type="password" name="user_password" placeholder="Придумайте пароль"
                                                 size="20" required></td>
                </tr>
                <tr>
                    <td class="add-table">Фамилия:</td>
                    <td class="add-table"><input type="text" name="user_surname" placeholder="Ваша фамилию"
                                                 value="${surname}" size="20" required></td>
                </tr>
                <tr>
                    <td class="add-table">Имя:</td>
                    <td class="add-table"><input type="text" name="user_firstName" placeholder="Ваше имя" size="20"
                                                 value="${firstName}" required></td>
                </tr>
                <tr>
                    <td class="add-table">Отчество:</td>
                    <td class="add-table"><input type="text" name="user_secondName" placeholder="Ваше отчество"
                                                 value="${secondName}" size="20" required></td>
                </tr>
                <tr>
                    <td class="add-table">Телефон:</td>
                    <td class="add-table"><input style="" type="text" name="user_phoneNumber" pattern="[0-9]{10,12}"
                                                 value="${phoneNumber}" placeholder="Ваш номер телефона" size="20"
                                                 required>
                    </td>
                </tr>
                <tr>
                    <td class="add-table">Фото *:</td>
                    <td class="add-table"><input type="file" name="user_avatar" accept="image/jpeg,image/png,image/gif">
                    </td>
                </tr>
            </table>
            <button>зарегистрироваться</button>
        </form>
        <c:if test="${message!=null}">
            <p class="message">${message}</p>
        </c:if>
        <form class="login-page" method="post">
            <input type="hidden" name="method" value="GO_LOGIN">
            <button>вход</button>
        </form>
    </div>
</div>
</body>
</html>