<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Должностя</title>
    <link rel="stylesheet" href="<c:url value="/resources/stylesheet/style.css"/>">
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
            <%--@elvariable id="positions" type="java.util.List"--%>
            <c:if test="${positions!=null}">
                <c:forEach items="${positions}" var="position">
                    <c:url value="/positions/update" var="update"><c:param name="id" value="${position.id}"/></c:url>
                    <c:url value="/positions/delete" var="delete"><c:param name="id" value="${position.id}"/></c:url>
                    <tr>
                        <td class="table-main">${position.id}</td>
                        <td class="table-main">${position.name}</td>
                        <td class="table-main">${position.description}</td>
                        <td class="table-main">
                            <a href="${update}"><img src="<c:url value="/resources/img/edit_icon.png"/>"></a>
                            <a href="${delete}"><img src="<c:url value="/resources/img/delete_icon.png"/>"></a>
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
        <c:if test="${positions!=null}">
            <%--pagination--%>
            <div class="pagination">
                    <%--@elvariable id="currentPage" type="java.lang.Integer"--%>
                <c:if test="${currentPage != 1}">
                    <c:url value="/positions" var="url">
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
                            <c:url value="/positions" var="url">
                                <c:param name="page" value="${i}"/>
                            </c:url>
                            <a href="${url}">${i}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <c:if test="${currentPage lt availablePages}">
                    <c:url value="/positions" var="url">
                        <c:param name="page" value="${currentPage + 1}"/>
                    </c:url>
                    <a href="${url}">>>></a>
                </c:if>
            </div>
        </c:if>
    </article>
    <aside>
        <jsp:include page="/WEB-INF/jsp/static/positions_menu.jsp"/>
    </aside>
</main>
<jsp:include page="/WEB-INF/jsp/static/bottom.jsp"/>
</body>
</html>