<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập - BookStore</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="auth-page">
        <div class="auth-card">
            <h1>📚 BookStore</h1>
            <h2 style="text-align:center; color:#666; margin-bottom:20px;">Đăng nhập</h2>

            <c:if test="${not empty error}">
                <div class="alert alert-error">${error}</div>
            </c:if>

            <c:if test="${param.verified == 'true'}">
                <div class="alert alert-success">
                    ✅ Tài khoản đã được xác thực. Hãy đăng nhập!
                </div>
            </c:if>

            <c:if test="${param.logout == 'true'}">
                <div class="alert alert-success">Bạn đã đăng xuất thành công.</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/login" method="post">
                <div class="form-group">
                    <label>Email</label>
                    <input type="email" name="email" required autofocus
                           value="${param.email}"
                           placeholder="Nhập email...">
                </div>

                <div class="form-group">
                    <label>Mật khẩu</label>
                    <input type="password" name="passwd" required
                           placeholder="Nhập mật khẩu...">
                </div>

                <button type="submit" class="btn btn-primary" style="width:100%;">
                    🔑 Đăng nhập
                </button>
            </form>

            <p style="text-align:center; margin-top:15px;">
                Chưa có tài khoản?
                <a href="${pageContext.request.contextPath}/register">Đăng ký</a>
            </p>
        </div>
    </div>
</body>
</html>