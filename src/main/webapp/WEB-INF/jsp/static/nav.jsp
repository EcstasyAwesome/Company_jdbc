<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.company.servlet.Authorization" %>
<nav>
    <table class="nav-menu">
        <tr>
            <td class="nav-menu-left">
                <a href="${pageContext.request.contextPath}/">Главная</a> |
                <a href="${pageContext.request.contextPath}/positions">Должностя</a> |
                <a href="${pageContext.request.contextPath}/users">Пользователи</a> |
                <a href="${pageContext.request.contextPath}/about">О компании</a>
            </td>
            <td class="nav-menu-right">
                <c:if test="${pageContext.session.getAttribute('sessionUser')!=null}">
                    <a href="${pageContext.request.contextPath}/profile">Профиль</a> |
                    <a href="${pageContext.request.contextPath}/edit">Редактировать</a> |
                    <form action="${pageContext.request.contextPath}${Authorization.AUTHORIZATION}" method="post">
                        <input type="hidden" name="method" value="LOGOUT">
                        <input type="submit" class="logout" value="Выход">
                    </form>
                </c:if>
                <c:if test="${pageContext.session.getAttribute('sessionUser')==null}">
                    <a href="${pageContext.request.contextPath}${Authorization.AUTHORIZATION}">Вход/регистрация</a>
                </c:if>
            </td>
        </tr>
    </table>
</nav>