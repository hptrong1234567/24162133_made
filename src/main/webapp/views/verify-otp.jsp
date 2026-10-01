<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Xác nhận OTP - BookStore</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="auth-page">
        <div class="auth-card">
            <h1>📧 Xác nhận OTP</h1>
            <p style="text-align:center; color:#666; margin-bottom:20px;">
                Mã OTP đã được gửi đến email của bạn.<br>
                Vui lòng kiểm tra hộp thư (kể cả Spam).
            </p>

            <c:if test="${not empty error}">
                <div class="alert alert-error">${error}</div>
            </c:if>

            <c:if test="${not empty success}">
                <div class="alert alert-success">${success}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/verify-otp" method="post">
                <div class="form-group">
                    <label>Email</label>
                    <input type="email" name="email" required
                           value="${not empty param.email ? param.email : email}"
                           readonly
                           style="background:#f1f5f9;">
                </div>

                <div class="form-group">
                    <label>Mã OTP (6 chữ số)</label>
                    <input type="text" name="otp" required
                           maxlength="6" pattern="[0-9]{6}"
                           inputmode="numeric"
                           placeholder="Nhập 6 số..."
                           style="font-size:20px; letter-spacing:5px; text-align:center;"
                           autofocus>
                </div>

                <button type="submit" class="btn btn-primary" style="width:100%;">
                    ✅ Xác nhận
                </button>
            </form>

            <!-- Gửi lại OTP -->
            <form action="${pageContext.request.contextPath}/verify-otp" method="post"
                  style="margin-top:15px;">
                <input type="hidden" name="action" value="resend">
                <input type="hidden" name="email"
                       value="${not empty param.email ? param.email : email}">
                <button type="submit" class="btn btn-warning" style="width:100%;">
                    🔄 Gửi lại OTP
                </button>
            </form>

            <p style="text-align:center; margin-top:15px;">
                <a href="${pageContext.request.contextPath}/login">← Quay lại Đăng nhập</a>
            </p>
        </div>
    </div>
</body>
</html>