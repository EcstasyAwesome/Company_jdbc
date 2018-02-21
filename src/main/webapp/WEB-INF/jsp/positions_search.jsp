<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.company.servlet.Positions" %>
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
                            <a href="${pageContext.request.contextPath}${Positions.UPDATE}?id=${position.id}">
                                <img src="${pageContext.request.contextPath}/resources/img/edit_icon.png">
                            </a>
                            <a href="${pageContext.request.contextPath}${Positions.DELETE}?id=${position.id}">
                                <img src="${pageContext.request.contextPath}/resources/img/delete_icon.png">
                            </a>
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
    </article>
    <aside>
        <jsp:include page="/WEB-INF/jsp/static/positions_menu.jsp"/>
    </aside>
</main>
<jsp:include page="/WEB-INF/jsp/static/bottom.jsp"/>
</body>
</html>