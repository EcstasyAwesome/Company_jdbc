<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header>
    <img src="${pageContext.request.contextPath}/resources/img/ecstasy_logo.jpg" alt="Логотип">
</header>
<nav>
    <div class="nav-menu-left">
        <a href="${pageContext.request.contextPath}/">Главная</a>
        <a href="${pageContext.request.contextPath}/positions">Должности</a>
        <a href="${pageContext.request.contextPath}/users">Пользователи</a>
        <a href="${pageContext.request.contextPath}/about">О компании</a>
    </div>
    <div class="nav-menu-right">
        <c:if test="${pageContext.session.getAttribute('sessionUser')!=null}">
            <form id="logout" action="${pageContext.request.contextPath}/authorization"
                  method="post">
                <input type="hidden" name="method" value="LOGOUT">
            </form>
            <a href="${pageContext.request.contextPath}/profile">Профиль</a>
            <a href="${pageContext.request.contextPath}/edit">Редактировать</a>
            <a onclick="document.getElementById('logout').submit()" href="#">Выход</a>
        </c:if>
        <c:if test="${pageContext.session.getAttribute('sessionUser')==null}">
            <form id="login" action="${pageContext.request.contextPath}/authorization"
                  class="login-page" method="post">
                <input type="hidden" name="method" value="GO_LOGIN">
            </form>
            <form id="register" action="${pageContext.request.contextPath}/authorization"
                  class="register-page" method="post">
                <input type="hidden" name="method" value="GO_REGISTER">
            </form>
            <a onclick="document.getElementById('login').submit()" href="#">Вход</a>
            <a onclick="document.getElementById('register').submit()" href="#">Регистрация</a>
        </c:if>
    </div>
</nav>