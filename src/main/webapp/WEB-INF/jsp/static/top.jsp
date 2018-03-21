<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.github.company.servlet.Authorization" %>
<%@ page import="com.github.company.servlet.Main" %>
<%@ page import="com.github.company.servlet.positions.PositionSearch" %>
<%@ page import="com.github.company.servlet.users.UserSearch" %>
<header>
    <img src="${pageContext.request.contextPath}/resources/img/ecstasy_logo.jpg" alt="Логотип">
</header>
<nav>
    <div class="nav-menu-left">
        <a href="${pageContext.request.contextPath}${Main.MAIN}">Главная</a>
        <a href="${pageContext.request.contextPath}${PositionSearch.MAIN}">Должности</a>
        <a href="${pageContext.request.contextPath}${UserSearch.MAIN}">Пользователи</a>
        <a href="${pageContext.request.contextPath}${Main.ABOUT}">О компании</a>
    </div>
    <div class="nav-menu-right">
        <c:if test="${pageContext.session.getAttribute('sessionUser')!=null}">
            <form id="logout" action="${pageContext.request.contextPath}${Authorization.AUTHORIZATION}"
                  method="post">
                <input type="hidden" name="method" value="LOGOUT">
            </form>
            <a href="${pageContext.request.contextPath}${Main.PROFILE}">Профиль</a>
            <a href="${pageContext.request.contextPath}${Main.EDIT}">Редактировать</a>
            <a onclick="document.getElementById('logout').submit()" href="#">Выход</a>
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
            <a onclick="document.getElementById('login').submit()" href="#">Вход</a>
            <a onclick="document.getElementById('register').submit()" href="#">Регистрация</a>
        </c:if>
    </div>
</nav>