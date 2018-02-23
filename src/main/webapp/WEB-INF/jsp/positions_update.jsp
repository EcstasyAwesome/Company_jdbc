<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Изменить должность</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/stylesheet/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/static/top.jsp"/>
<main>
    <article>
        <form id="update" method="post">
            <input type="hidden" name="method" value="UPDATE">
            <input type="hidden" name="id" value="${position.id}">
            <p>* - поля, доступные для изменения</p>
            <table align="center">
                <tr>
                    <th width="40" class="table-top">ID</th>
                    <th width="200" class="table-top">Должность*</th>
                    <th width="400" class="table-top">Доп. информация*</th>
                </tr>
                <c:if test="${position!=null}">
                    <tr>
                        <td class="table-main">${position.id}</td>
                        <td class="table-main">
                            <input class="transparent-input" value="${position.name}" name="name" autofocus required>
                        </td>
                        <td class="table-main">
                            <input class="transparent-input" value="${position.description}" name="description" required>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3" align="center">
                            <c:if test="${positionError!=null}">
                                <p class="server-answer">${positionError}</p>
                            </c:if>
                            <br><input type="submit" value="Сохранить изменения">
                        </td>
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