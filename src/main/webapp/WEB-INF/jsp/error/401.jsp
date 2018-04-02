<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isErrorPage="true" %>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>UNAUTHORIZED</title>
    <link rel="stylesheet" href="<c:url value="/resources/stylesheet/frame.css"/>">
</head>
<body>
<div class="page">
    <div class="form">
        <img src="<c:url value="/resources/img/401.png"/>">
        <form action="<c:url value="/authorization"/>" method="post">
            <input type="hidden" name="method" value="GO_LOGIN">
            <br>
            <button>вход</button>
        </form>
        <form action="<c:url value="/authorization"/>" method="post">
            <input type="hidden" name="method" value="GO_REGISTER">
            <button>регистрация</button>
        </form>
        <form action="<c:url value="/"/>">
            <button>главная</button>
        </form>
    </div>
</div>
</body>
</html>