<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Главная</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/stylesheet/style.css">
</head>
<body>
<jsp:include page="WEB-INF/jsp/static/header.jsp"/>
<jsp:include page="WEB-INF/jsp/static/nav.jsp"/>
<main>
    <h2 style="text-align: center">Добро пожаловать</h2>
</main>
<jsp:include page="WEB-INF/jsp/static/footer.jsp"/>
</body>
</html>