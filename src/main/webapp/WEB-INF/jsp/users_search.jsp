<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.github.company.servlet.users.UserSearch" %>
<%@ page import="com.github.company.servlet.users.UserUpdate" %>
<%@ page import="com.github.company.servlet.users.UserDelete" %>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Пользователи</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/stylesheet/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/static/top.jsp"/>
<main>
    <article>
        <table align="center">
            <tr>
                <th width="40" class="table-top">ID</th>
                <th width="130" class="table-top">Фамилия</th>
                <th width="130" class="table-top">Имя</th>
                <th width="130" class="table-top">Отчество</th>
                <th width="130" class="table-top">Телефон</th>
                <th width="130" class="table-top">Должность</th>
                <th width="130" class="table-top">Группа</th>
            </tr>
            <c:if test="${users!=null}">
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td class="table-main">${user.id}</td>
                        <td class="table-main">${user.surname}</td>
                        <td class="table-main">${user.firstName}</td>
                        <td class="table-main">${user.middleName}</td>
                        <td class="table-main">${user.phone}</td>
                        <td class="table-main">${user.position.name}</td>
                        <td class="table-main">${user.group.name}</td>
                        <td class="table-main">
                            <a href="${pageContext.request.contextPath}${UserUpdate.UPDATE}?id=${user.id}">
                                <img src="${pageContext.request.contextPath}/resources/img/edit_icon.png">
                            </a>
                            <a href="${pageContext.request.contextPath}${UserDelete.DELETE}?id=${user.id}">
                                <img src="${pageContext.request.contextPath}/resources/img/delete_icon.png">
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
            <c:if test="${users==null || users.isEmpty()}">
                <tr>
                    <td class="table-main" colspan="7">Данные отсуствуют</td>
                </tr>
            </c:if>
        </table>
        <c:if test="${users!=null}">
            <div class="pagination">
                <c:if test="${currentPage != 1}">
                    <a href="${pageContext.request.contextPath}${UserSearch.MAIN}?page=${currentPage - 1}">${currentPage - 1}</a>
                </c:if>
                <a class="current-link">${currentPage}</a>
                <c:if test="${currentPage lt availablePages}">
                    <a href="${pageContext.request.contextPath}${UserSearch.MAIN}?page=${currentPage + 1}">${currentPage + 1}</a>
                </c:if>
            </div>
        </c:if>
    </article>
    <aside>
        <jsp:include page="/WEB-INF/jsp/static/users_menu.jsp"/>
    </aside>
</main>
<jsp:include page="/WEB-INF/jsp/static/bottom.jsp"/>
</body>
</html>