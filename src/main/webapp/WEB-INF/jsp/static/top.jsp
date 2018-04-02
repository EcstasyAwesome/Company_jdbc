<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header>
    <img src="<c:url value="/resources/img/ecstasy_logo.jpg"/>" alt="Логотип">
</header>
<nav>
    <div class="nav-menu-left">
        <a href="<c:url value="/"/>">Главная</a>
        <a href="<c:url value="/positions"/>">Должности</a>
        <a href="<c:url value="/users"/>">Пользователи</a>
        <a href="<c:url value="/about"/>">О компании</a>
    </div>
    <div class="nav-menu-right">
        <c:if test="${pageContext.session.getAttribute('sessionUser')!=null}">
            <form id="logout" action="<c:url value="/authorization"/>"
                  method="post">
                <input type="hidden" name="method" value="LOGOUT">
            </form>
            <a href="<c:url value="/profile"/>">Профиль</a>
            <a href="<c:url value="/edit"/>">Редактировать</a>
            <a onclick="document.getElementById('logout').submit()" href="#">Выход</a>
        </c:if>
        <c:if test="${pageContext.session.getAttribute('sessionUser')==null}">
            <form id="login" action="<c:url value="/authorization"/>"
                  class="login-page" method="post">
                <input type="hidden" name="method" value="GO_LOGIN">
            </form>
            <form id="register" action="<c:url value="/authorization"/>"
                  class="register-page" method="post">
                <input type="hidden" name="method" value="GO_REGISTER">
            </form>
            <a onclick="document.getElementById('login').submit()" href="#">Вход</a>
            <a onclick="document.getElementById('register').submit()" href="#">Регистрация</a>
        </c:if>
    </div>
</nav>