<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isErrorPage="true" %>
<%@ page import="com.github.company.servlet.Authorization" %>
<%@ page import="com.github.company.servlet.Main" %>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>UNAUTHORIZED</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/stylesheet/frame.css">
</head>
<body>
<div class="page">
    <div class="form">
        <img src="${pageContext.request.contextPath}/resources/img/401.png" width="330">
        <form action="${pageContext.request.contextPath}${Authorization.AUTHORIZATION}" method="post">
            <input type="hidden" name="method" value="GO_LOGIN">
            <br><button>вход</button>
        </form>
        <form action="${pageContext.request.contextPath}${Authorization.AUTHORIZATION}" method="post">
            <input type="hidden" name="method" value="GO_REGISTER">
            <button>регистрация</button>
        </form>
        <form action="${pageContext.request.contextPath}${Main.MAIN}">
            <button>главная</button>
        </form>
    </div>
</div>
</body>
</html>