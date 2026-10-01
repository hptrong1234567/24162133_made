<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng ký - BookStore</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="auth-page">
        <div class="auth-card">
            <h1>📚 Đăng ký tài khoản</h1>

            <c:if test="${not empty error}">
                <div class="alert alert-error">${error}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/register" method="post">
                <div class="form-group">
                    <label>Email *</label>
                    <input type="email" name="email" required autofocus
                           value="${param.email}">
                </div>

                <div class="form-group">
                    <label>Họ tên *</label>
                    <input type="text" name="fullname" required
                           value="${param.fullname}">
                </div>

                <div class="form-group">
                    <label>Số điện thoại</label>
                    <input type="number" name="phone"
                           value="${param.phone}">
                </div>

                <div class="form-group">
                    <label>Mật khẩu *</label>
                    <input type="password" name="passwd" required minlength="6">
                </div>

                <div class="form-group">
                    <label>Xác nhận mật khẩu *</label>
                    <input type="password" name="confirmPasswd" required minlength="6">
                </div>

                <button type="submit" class="btn btn-primary" style="width:100%;">
                    Đăng ký & Nhận OTP
                </button>
            </form>

            <p style="text-align:center; margin-top:15px;">
                Đã có tài khoản?
                <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
            </p>
        </div>
    </div>
</body>
</html>