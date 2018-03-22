<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Добавить пользователя</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/stylesheet/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/static/top.jsp"/>
<main>
    <article>
        <form method="post">
            <table align="center">
                <tr>
                    <th width="130" class="table-top">Логин</th>
                    <th width="130" class="table-top">Пароль</th>
                    <th width="130" class="table-top">Фамилия</th>
                    <th width="130" class="table-top">Имя</th>
                    <th width="130" class="table-top">Отчество</th>
                    <th width="130" class="table-top">Телефон</th>
                    <th width="130" class="table-top">Должность</th>
                    <th width="130" class="table-top">Группа</th>
                </tr>
                <tr>
                    <td class="table-main">
                        <input class="transparent-input" value="${user.login}" name="login" autofocus required>
                    </td>
                    <td class="table-main">
                        <input class="transparent-input" type="password" name="password" required>
                    </td>
                    <td class="table-main">
                        <input class="transparent-input" value="${user.surname}" name="surname" required>
                    </td>
                    <td class="table-main">
                        <input class="transparent-input" value="${user.firstName}" name="firstName" required>
                    </td>
                    <td class="table-main">
                        <input class="transparent-input" value="${user.middleName}" name="middleName" required>
                    </td>
                    <td class="table-main">
                        <input class="transparent-input" type="text" name="phone" pattern="380[0-9]{9}"
                               <c:if test="${phone==null}">value="380"</c:if>
                               <c:if test="${phone!=null}">value="${phone}"</c:if>
                               required>
                    </td>
                    <td class="table-main">
                        <select class="transparent-input" name="position" required>
                            <option selected disabled>Должность</option>
                            <c:forEach items="${positions}" var="that">
                                <option value="${that.id}">${that.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td class="table-main">
                        <select class="transparent-input" name="group" required>
                            <option selected disabled>Группа</option>
                            <c:forEach items="${groups}" var="that">
                                <option value="${that.id}">${that.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="8" align="center">
                        <c:if test="${userError!=null}">
                            <span class="server-answer">${userError}</span>
                        </c:if>
                        <br><input type="submit" value="Добавить"></td>
                </tr>
            </table>
        </form>
    </article>
    <aside>
        <jsp:include page="/WEB-INF/jsp/static/users_menu.jsp"/>
    </aside>
</main>
<jsp:include page="/WEB-INF/jsp/static/bottom.jsp"/>
</body>
</html>