<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isErrorPage="true" %>
<%@ page import="com.company.servlet.Main" %>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>NOT FOUND</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/stylesheet/frame.css">
</head>
<body>
<div class="page">
    <div class="form">
        <img src="${pageContext.request.contextPath}/resources/img/404.png" width="330">
        <button onclick="history.back()">Назад</button>
        <form action="${pageContext.request.contextPath}${Main.MAIN}">
            <br>
            <button>главная</button>
        </form>
    </div>
</div>
</body>
</html>