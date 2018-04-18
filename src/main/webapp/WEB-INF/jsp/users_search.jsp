<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Пользователи</title>
    <link rel="stylesheet" href="<c:url value="/resources/stylesheet/style.css"/>">
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
                <th width="150" class="table-top">Телефон</th>
                <th width="130" class="table-top">Должность</th>
                <th width="130" class="table-top">Группа</th>
            </tr>
            <%--@elvariable id="users" type="java.util.List"--%>
            <c:if test="${users!=null}">
                <c:forEach items="${users}" var="user">
                    <c:url value="/users/update" var="update"><c:param name="id" value="${user.id}"/></c:url>
                    <c:url value="/users/delete" var="delete"><c:param name="id" value="${user.id}"/></c:url>
                    <tr>
                        <td class="table-main">${user.id}</td>
                        <td class="table-main">${user.surname}</td>
                        <td class="table-main">${user.firstName}</td>
                        <td class="table-main">${user.middleName}</td>
                        <c:set value="${user.phone}" var="p"/>
                        <td class="table-main">
                            +${fn:substring(p, 0, 2)}(${fn:substring(p, 2, 5)})-${fn:substring(p, 5, 8)}-${fn:substring(p, 8, 12)}
                        </td>
                        <td class="table-main">${user.position.name}</td>
                        <td class="table-main">${user.group.name}</td>
                        <td class="table-main">
                            <a href="${update}"><img src="<c:url value="/resources/img/edit_icon.png"/>"></a>
                            <a href="${delete}"><img src="<c:url value="/resources/img/delete_icon.png"/>"></a>
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
            <%--pagination--%>
            <div class="pagination">
                    <%--@elvariable id="currentPage" type="java.lang.Integer"--%>
                <c:if test="${currentPage != 1}">
                    <c:url value="/users" var="url">
                        <c:param name="page" value="${currentPage - 1}"/>
                    </c:url>
                    <a href="${url}"><<<</a>
                </c:if>
                    <%--@elvariable id="availablePages" type="java.lang.Integer"--%>
                <c:forEach begin="1" end="${availablePages}" var="i">
                    <c:choose>
                        <c:when test="${currentPage eq i}">
                            <a class="current-link">${i}</a>
                        </c:when>
                        <c:otherwise>
                            <c:url value="/users" var="url">
                                <c:param name="page" value="${i}"/>
                            </c:url>
                            <a href="${url}">${i}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <c:if test="${currentPage lt availablePages}">
                    <c:url value="/users" var="url">
                        <c:param name="page" value="${currentPage + 1}"/>
                    </c:url>
                    <a href="${url}">>>></a>
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