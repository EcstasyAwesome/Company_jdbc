<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>ERROR 404</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/stylesheet/login.css">
</head>
<body>
<div class="page">
    <div class="form">
        <img src="${pageContext.request.contextPath}/resources/img/404.png" width="300">
        <c:if test="${pageContext.session.getAttribute('previousPage')!=null}">
            <form action="${pageContext.session.getAttribute('previousPage')}">
                <button>назад</button>
            </form>
        </c:if>
        <form action="/">
            <button>главная</button>
        </form>
    </div>
</div>
</body>
</html>