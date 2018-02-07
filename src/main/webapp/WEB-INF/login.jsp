<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Авторизация</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/stylesheet/login.css">
</head>
<body>
<div class="page">
    <div class="form">
        <form class="login-form" method="post">
            <input type="hidden" name="method" value="LOGIN">
            <input type="text" name="login" value="${login}" placeholder="логин" required autofocus/>
            <input type="password" name="password" placeholder="пароль" required/>
            <button>войти</button>
        </form>
        <c:if test="${message!=null}">
            <p class="message">${message}</p>
        </c:if>
        <form class="register-page" method="post">
            <input type="hidden" name="method" value="GO_REGISTER">
            <button>регистрация</button>
        </form>
    </div>
</div>
</body>
</html>