<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Удалить должность</title>
    <link rel="stylesheet" href="<c:url value="/resources/stylesheet/style.css"/>">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/static/top.jsp"/>
<main>
    <article>
        <form method="post">
            <%--@elvariable id="position" type="com.github.company.dao.entity.Position"--%>
            <input type="hidden" name="id" value="${position.id}">
            <table align="center">
                <tr>
                    <th width="40" class="table-top">ID</th>
                    <th width="200" class="table-top">Должность</th>
                    <th width="400" class="table-top">Доп. информация</th>
                </tr>
                <c:if test="${position!=null}">
                    <tr>
                        <td class="table-main">${position.id}</td>
                        <td class="table-main">${position.name}</td>
                        <td class="table-main">${position.description}</td>
                    </tr>
                    <tr>
                        <td colspan="3" align="center"><br>Будут удалены все работники на этой должности!
                            <br>Удалить данную должность?
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3" align="center">
                            <br><input type="submit" value="Удалить"></td>
                    </tr>
                </c:if>
                <c:if test="${position==null}">
                    <tr>
                        <td class="table-main" colspan="3">Запись уже не существует</td>
                    </tr>
                </c:if>
            </table>
        </form>
    </article>
    <aside>
        <jsp:include page="/WEB-INF/jsp/static/positions_menu.jsp"/>
    </aside>
</main>
<jsp:include page="/WEB-INF/jsp/static/bottom.jsp"/>
</body>
</html>