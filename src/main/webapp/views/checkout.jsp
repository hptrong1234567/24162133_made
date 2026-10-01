<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thanh toán - BookStore</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .checkout-grid {
            display: grid;
            grid-template-columns: 2fr 1fr;
            gap: 20px;
        }
        .checkout-box {
            background: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
        }
        .checkout-box h2 {
            color: #172554;
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 2px solid #f1f5f9;
        }
        .order-items {
            list-style: none;
        }
        .order-items li {
            display: flex;
            justify-content: space-between;
            padding: 10px 0;
            border-bottom: 1px solid #f1f5f9;
        }
        .payment-method {
            padding: 15px;
            background: #f0fdf4;
            border: 2px solid #16a34a;
            border-radius: 8px;
            margin-top: 15px;
        }
        .payment-method .badge {
            background: #16a34a;
            color: white;
            padding: 4px 10px;
            border-radius: 4px;
            font-size: 12px;
        }
        .total-box {
            background: #fef2f2;
            padding: 15px;
            border-radius: 8px;
            margin-top: 15px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .total-box .amount {
            color: #dc2626;
            font-size: 22px;
            font-weight: bold;
        }
    </style>
</head>
<body>

    <!-- HEADER -->
    <header class="header">
        <div class="brand">
            <a href="${pageContext.request.contextPath}/home">📚 BookStore</a>
        </div>
        <nav>
            <a href="${pageContext.request.contextPath}/home">Trang Chủ</a>
            <a href="${pageContext.request.contextPath}/cart">🛒 Giỏ hàng</a>
            <a href="${pageContext.request.contextPath}/order-history">📦 Đơn hàng</a>
        </nav>
        <div class="account">
            <span>Xin chào, <b>${sessionScope.account.fullname}</b></span>
            <a href="${pageContext.request.contextPath}/logout" class="btn-logout">Đăng xuất</a>
        </div>
    </header>

    <main class="container">
        <h1>💳 Thanh toán đơn hàng</h1>

        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/checkout" method="post">
            <div class="checkout-grid">

                <!-- Cột trái: Thông tin giao hàng -->
                <div class="checkout-box">
                    <h2>📍 Thông tin giao hàng</h2>

                    <div class="form-group">
                        <label>Địa chỉ giao hàng *</label>
                        <textarea name="shippingAddress" rows="3" required
                                  placeholder="Số nhà, đường, phường/xã, quận/huyện, tỉnh/thành phố..."></textarea>
                    </div>

                    <div class="form-group">
                        <label>Số điện thoại *</label>
                        <input type="tel" name="phone" required
                               pattern="[0-9]{10,11}"
                               value="${sessionScope.account.phone}"
                               placeholder="VD: 0901234567">
                    </div>

                    <div class="form-group">
                        <label>Ghi chú (tùy chọn)</label>
                        <textarea name="note" rows="2"
                                  placeholder="VD: Giao giờ hành chính..."></textarea>
                    </div>

                    <h2 style="margin-top:25px;">💰 Phương thức thanh toán</h2>

                    <div class="payment-method">
                        <div style="display:flex; align-items:center; gap:10px;">
                            <input type="radio" name="paymentMethod" value="COD" checked id="cod">
                            <label for="cod" style="margin:0; cursor:pointer;">
                                <b>Thanh toán khi nhận hàng (COD)</b>
                                <span class="badge">Mặc định</span>
                            </label>
                        </div>
                        <p style="color:#666; margin-top:8px; margin-left:25px; font-size:14px;">
                            Bạn sẽ thanh toán bằng tiền mặt khi nhận hàng từ shipper.
                        </p>
                    </div>
                </div>

                <!-- Cột phải: Đơn hàng -->
                <div class="checkout-box">
                    <h2>📦 Đơn hàng của bạn</h2>

                    <ul class="order-items">
                        <c:forEach items="${cartItems}" var="item">
                            <li>
                                <div>
                                    <div style="font-weight:bold;">${item.book.title}</div>
                                    <div style="color:#666; font-size:13px;">
                                        SL: ${item.quantity} ×
                                        <fmt:formatNumber value="${item.book.price}" type="number" groupingUsed="true"/>đ
                                    </div>
                                </div>
                                <div style="color:#dc2626; font-weight:bold;">
                                    <fmt:formatNumber value="${item.book.price * item.quantity}"
                                                      type="number" groupingUsed="true"/>đ
                                </div>
                            </li>
                        </c:forEach>
                    </ul>

                    <div class="total-box">
                        <span>Tổng tiền:</span>
                        <span class="amount">
                            <fmt:formatNumber value="${totalAmount}" type="number" groupingUsed="true"/> VNĐ
                        </span>
                    </div>

                    <button type="submit" class="btn btn-success"
                            style="width:100%; margin-top:20px; padding:14px; font-size:16px;"
                            onclick="return confirm('Xác nhận đặt hàng COD?')">
                        ✅ Xác nhận đặt hàng COD
                    </button>

                    <a href="${pageContext.request.contextPath}/cart"
                       class="btn" style="display:block; text-align:center; margin-top:10px; background:#6b7280; color:white;">
                        ← Quay lại giỏ hàng
                    </a>
                </div>

            </div>
        </form>
    </main>

    <footer class="footer">
        <p><b>Họ tên:</b> Huỳnh Phú Trọng | <b>MSSV:</b> 24162133 | <b>Mã đề:</b> 01</p>
    </footer>

</body>
</html>