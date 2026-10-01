<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<header class="header">
    <div class="brand">
        <a href="${pageContext.request.contextPath}/home">📚 BookStore</a>
    </div>
    
    <nav>
        <a href="${pageContext.request.contextPath}/home">Trang Chủ</a>
        <a href="${pageContext.request.contextPath}/home">Sản phẩm</a>
        
        <c:if test="${sessionScope.account != null}">
            <a href="${pageContext.request.contextPath}/cart">🛒 Giỏ hàng</a>
            <a href="${pageContext.request.contextPath}/order-history">📦 Đơn hàng</a>
        </c:if>
        
        <c:if test="${sessionScope.account != null and sessionScope.account.isAdmin}">
            <a href="${pageContext.request.contextPath}/admin/books">Quản trị</a>
        </c:if>
    </nav>
    
    <div class="account">
        <c:choose>
            <c:when test="${sessionScope.account != null}">
                <span>Xin chào, <b>${sessionScope.account.fullname}</b></span>
                <a href="${pageContext.request.contextPath}/logout" class="btn-logout">Đăng xuất</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/login" class="btn-login">Đăng nhập</a>
            </c:otherwise>
        </c:choose>
    </div>
</header>