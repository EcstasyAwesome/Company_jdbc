<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Должностя</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/stylesheet/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/static/top.jsp"/>
<main>
    <article>
        <br>
        <table align="center">
            <tr>
                <th width="40" class="table-top">ID</th>
                <th width="200" class="table-top">Должность</th>
                <th width="400" class="table-top">Доп. информация</th>
            </tr>
            <c:if test="${positions!=null}">
                <c:forEach items="${positions}" var="position">
                    <tr>
                        <td class="table-main">${position.id}</td>
                        <td class="table-main">${position.name}</td>
                        <td class="table-main">${position.description}</td>
                        <td class="table-main">
                            <a href="/positions/update?id=${position.id}"><img src="${pageContext.request.contextPath}/resources/img/edit_icon.png"></a>
                            <a href="/positions/delete?id=${position.id}"><img src="${pageContext.request.contextPath}/resources/img/delete_icon.png"></a>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
            <c:if test="${positions==null || positions.isEmpty()}">
                <tr>
                    <td class="table-main" colspan="3">Данные отсуствуют</td>
                </tr>
            </c:if>
        </table>
        <br>
    </article>
    <aside>
        <h4>Меню:</h4>
        <menu>
            <li><a href="/positions">Список</a></li>
            <li><a href="/positions/add">Добавить</a></li>
        </menu>
    </aside>
</main>
<jsp:include page="/WEB-INF/jsp/static/bottom.jsp"/>
</body>
</html>