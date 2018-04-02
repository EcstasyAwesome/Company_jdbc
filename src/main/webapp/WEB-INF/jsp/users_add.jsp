<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Добавить пользователя</title>
    <link rel="stylesheet" href="<c:url value="/resources/stylesheet/style.css"/>">
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
                        <%--@elvariable id="user" type="com.github.company.dao.entity.User"--%>
                        <input title="Логин" class="transparent-input" value="${user.login}" name="login"
                               autofocus required>
                    </td>
                    <td class="table-main">
                        <input title="Пароль" class="transparent-input" value="${user.password}" name="password"
                               required>
                    </td>
                    <td class="table-main">
                        <input title="Фамилия" class="transparent-input" value="${user.surname}" name="surname"
                               required>
                    </td>
                    <td class="table-main">
                        <input title="Имя" class="transparent-input" value="${user.firstName}" name="firstName"
                               required>
                    </td>
                    <td class="table-main">
                        <input title="Отчество" class="transparent-input" value="${user.middleName}" name="middleName"
                               required>
                    </td>
                    <td class="table-main">
                        <input title="Телефон" class="transparent-input" type="text" name="phone" pattern="380[0-9]{9}"
                               <c:if test="${user.phone==null}">value="380"</c:if>
                               <c:if test="${user.phone!=null}">value="${user.phone}"</c:if>
                               required>
                    </td>
                    <td class="table-main">
                        <select title="Должность" class="transparent-input" name="position" required>
                            <option selected disabled>Должность</option>
                            <%--@elvariable id="positions" type="java.util.List"--%>
                            <c:forEach items="${positions}" var="that">
                                <c:if test="${that.id==user.position.id}">
                                    <option selected value="${that.id}">${that.name}</option>
                                </c:if>
                                <c:if test="${that.id!=user.position.id}">
                                    <option value="${that.id}">${that.name}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </td>
                    <td class="table-main">
                        <select title="Группа" class="transparent-input" name="group" required>
                            <option selected disabled>Группа</option>
                            <%--@elvariable id="groups" type="java.util.List"--%>
                            <c:forEach items="${groups}" var="that">
                                <c:if test="${that.id==user.group.id}">
                                    <option selected value="${that.id}">${that.name}</option>
                                </c:if>
                                <c:if test="${that.id!=user.group.id}">
                                    <option value="${that.id}">${that.name}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="8" align="center">
                        <%--@elvariable id="userError" type="java.lang.String"--%>
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