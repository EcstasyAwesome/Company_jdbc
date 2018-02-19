<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Удалить пользователя</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/stylesheet/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/static/top.jsp"/>
<main>
    <article>
        <form method="post">
            <input type="hidden" name="method" value="DELETE">
            <input type="hidden" name="form" value="deleteUser">
            <select name="user_id" required>
                <option selected disabled>Выберите ID</option>
                <c:if test="${users!=null}">
                    <c:forEach items="${users}" var="user">
                        <option value="${user.getId()}">ID${user.getId()}
                            - ${user.getSurname()} ${user.getFirstName()} ${user.getSecondName()}</option>
                    </c:forEach>
                </c:if>
                <c:if test="${users.isEmpty()}">
                    <option disabled>Список пуст</option>
                </c:if>
            </select>
            <input type="submit" value="Удалить">
        </form>
    </article>
    <aside>
        <h4>Меню:</h4>
        <menu>
            <li><a href="${pageContext.request.contextPath}/users">Список/Поиск</a></li>
            <li><a href="${pageContext.request.contextPath}/users/add">Добавить</a></li>
        </menu>
    </aside>
</main>
<jsp:include page="/WEB-INF/jsp/static/bottom.jsp"/>
</body>
</html>