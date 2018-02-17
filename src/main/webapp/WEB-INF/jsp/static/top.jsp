<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.company.servlet.Authorization" %>
<header>
    <img src="${pageContext.request.contextPath}/resources/img/ecstasy_logo.jpg" alt="Логотип">
</header>
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
                    <form action="${pageContext.request.contextPath}${Authorization.LINK_AUTHORIZATION}" method="post">
                        <input type="hidden" name="method" value="LOGOUT">
                        <input type="submit" class="logout" value="Выход">
                    </form>
                </c:if>
                <c:if test="${pageContext.session.getAttribute('sessionUser')==null}">
                    <form id="GO_LOGIN" action="${pageContext.request.contextPath}${Authorization.LINK_AUTHORIZATION}" class="login-page" method="post">
                        <input type="hidden" name="method" value="GO_LOGIN">
                    </form>
                    <form id="GO_REGISTER" action="${pageContext.request.contextPath}${Authorization.LINK_AUTHORIZATION}" class="register-page" method="post">
                        <input type="hidden" name="method" value="GO_REGISTER">
                    </form>
                        <input form="GO_LOGIN" type="submit" class="logout" value="Вход"> |
                        <input form="GO_REGISTER" type="submit" class="logout" value="Регистрация">
                </c:if>
            </td>
        </tr>
    </table>
</nav>