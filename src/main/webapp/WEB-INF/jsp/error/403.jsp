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
        <img src="<c:url value="/resources/img/403.png"/>">
        <br>
        <br>
        <button onclick="history.back()">Назад</button>
        <form action="<c:url value="/"/>">
            <br>
            <button>главная</button>
        </form>
    </div>
</div>
</body>
</html>