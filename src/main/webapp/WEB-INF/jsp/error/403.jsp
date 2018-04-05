<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isErrorPage="true" %>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>FORBIDDEN</title>
    <link rel="stylesheet" href="<c:url value="/resources/stylesheet/frame.css"/>">
</head>
<body>
<div class="page">
    <div class="form">
        <img src="<c:url value="/resources/img/logo.png"/>">
        <div class="error-block">
            <div class="error-name">Forbidden operation</div>
            <div class="error-description">Your request can not currently be completed</div>
            <div class="error-support">You can contact our technical staff to inform them of the problem by sending an e-mail to <a href="mailto:ecstasy.awesome@gmail.com">ecstasy.awesome@gmail.com</a>.</div>
        </div>
        <button onclick="history.back()">Назад</button>
        <form action="<c:url value="/"/>">
            <br>
            <button>главная</button>
        </form>
    </div>
</div>
</body>
</html>