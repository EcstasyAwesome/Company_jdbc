<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Авторизация</title>
    <link rel="stylesheet" href="<c:url value="/resources/stylesheet/frame.css"/>">
</head>
<body>
<div class="page">
    <div class="form">
        <form class="login-form" method="post">
            <input type="hidden" name="method" value="LOGIN">
            <%--@elvariable id="login" type="java.lang.String"--%>
            <input type="text" name="login" value="${login}" placeholder="логин" required autofocus/>
            <input type="password" name="password" placeholder="пароль" required/>
            <button>войти</button>
        </form>
        <%--@elvariable id="message" type="java.lang.String"--%>
        <c:if test="${message!=null}">
            <p class="server-answer">${message}</p><br>
        </c:if>
        <form class="register-page" method="post">
            <input type="hidden" name="method" value="GO_REGISTER">
            <button>регистрация</button>
        </form>
    </div>
</div>
</body>
</html>