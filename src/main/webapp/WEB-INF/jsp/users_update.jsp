<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Изменить пользователя</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/stylesheet/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/static/top.jsp"/>
<main>
    <article>
        <form id="update" method="post">
            <input type="hidden" name="method" value="UPDATE">
            <input type="hidden" name="id" value="${user.id}">
            <p>* - поля, доступные для изменения</p>
            <table align="center">
                <tr>
                    <th width="40" class="table-top">ID</th>
                    <th width="130" class="table-top">Фамилия*</th>
                    <th width="130" class="table-top">Имя*</th>
                    <th width="130" class="table-top">Отчество*</th>
                    <th width="130" class="table-top">Телефон*</th>
                    <th width="130" class="table-top">Должность*</th>
                    <th width="130" class="table-top">Группа*</th>
                </tr>
                <c:if test="${user!=null}">
                    <tr>
                        <td class="table-main">${user.id}</td>
                        <td class="table-main">
                            <input class="transparent-input" value="${user.surname}" name="surname" autofocus required>
                        </td>
                        <td class="table-main">
                            <input class="transparent-input" value="${user.firstName}" name="firstName" required>
                        </td>
                        <td class="table-main">
                            <input class="transparent-input" value="${user.middleName}" name="middleName" required>
                        </td>
                        <td class="table-main">
                            <input class="transparent-input" value="${user.phone}" name="phone" required>
                        </td>
                        <td class="table-main">
                            <select class="transparent-input" name="position">
                                <c:forEach items="${positions}" var="that">
                                    <c:if test="${user.position.id==that.id}">
                                        <option selected value="${that.id}">${that.name}</option>
                                    </c:if>
                                    <c:if test="${user.position.id!=that.id}">
                                        <option value="${that.id}">${that.name}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </td>
                        <td class="table-main">
                            <select class="transparent-input" name="group">
                                <c:forEach items="${groups}" var="that">
                                    <c:if test="${user.group.id==that.id}">
                                        <option selected value="${that.id}">${that.name}</option>
                                    </c:if>
                                    <c:if test="${user.group.id!=that.id}">
                                        <option value="${that.id}">${that.name}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="7" align="center">
                            <c:if test="${userError!=null}">
                                <p class="server-answer">${userError}</p>
                            </c:if>
                            <br><input type="submit" value="Сохранить изменения">
                        </td>
                    </tr>
                </c:if>
                <c:if test="${user==null}">
                    <tr>
                        <td class="table-main" colspan="7">Запись уже не существует</td>
                    </tr>
                </c:if>
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