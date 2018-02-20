<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.company.servlet.Authorization" %>
<header>
    <img src="${pageContext.request.contextPath}/resources/img/ecstasy_logo.jpg" alt="Логотип">
</header>
<nav>
    <div class="nav-menu-left">
    <ul>
        <li><a href="${pageContext.request.contextPath}/">Главная</a></li>
        <li><a href="${pageContext.request.contextPath}/positions">Должностя</a></li>
        <li><a href="${pageContext.request.contextPath}/users">Пользователи</a></li>
        <li><a href="${pageContext.request.contextPath}/about">О компании</a></li>
    </ul>
    </div>
    <div class="nav-menu-right">
        <ul>
            <c:if test="${pageContext.session.getAttribute('sessionUser')!=null}">
                <form id="logout" action="${pageContext.request.contextPath}${Authorization.AUTHORIZATION}"
                      method="post">
                    <input type="hidden" name="method" value="LOGOUT">
                </form>
                <li><a href="${pageContext.request.contextPath}/profile">Профиль</a></li>
                <li><a href="${pageContext.request.contextPath}/edit">Редактировать</a></li>
                <li><a onclick="document.getElementById('logout').submit()" href="#">Выход</a></li>
            </c:if>
            <c:if test="${pageContext.session.getAttribute('sessionUser')==null}">
                <form id="login" action="${pageContext.request.contextPath}${Authorization.AUTHORIZATION}"
                      class="login-page" method="post">
                    <input type="hidden" name="method" value="GO_LOGIN">
                </form>
                <form id="register" action="${pageContext.request.contextPath}${Authorization.AUTHORIZATION}"
                      class="register-page" method="post">
                    <input type="hidden" name="method" value="GO_REGISTER">
                </form>
                <li><a onclick="document.getElementById('login').submit()" href="#">Вход</a></li>
                <li><a onclick="document.getElementById('register').submit()" href="#">Регистрация</a></li>
            </c:if>
        </ul>
    </div>
</nav>