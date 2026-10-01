<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Giỏ hàng - BookStore</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .cart-table {
            width: 100%;
            background: white;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
        }
        .cart-table th, .cart-table td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #e5e7eb;
        }
        .cart-table th {
            background: #f3f4f6;
            font-weight: bold;
        }
        .cart-table img {
            width: 60px;
            height: 60px;
            object-fit: cover;
            border-radius: 6px;
        }
        .qty-form {
            display: flex;
            gap: 5px;
            align-items: center;
        }
        .qty-form input[type="number"] {
            width: 70px;
            padding: 6px;
            border: 1px solid #d1d5db;
            border-radius: 4px;
            text-align: center;
        }
        .cart-summary {
            margin-top: 20px;
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .cart-summary .total {
            font-size: 22px;
            color: #dc2626;
            font-weight: bold;
        }
        .cart-actions {
            display: flex;
            gap: 10px;
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
        <h1>🛒 Giỏ hàng của bạn</h1>

        <!-- Thông báo -->
        <c:if test="${param.added == 'true'}">
            <div class="alert alert-success">✅ Đã thêm sách vào giỏ hàng!</div>
        </c:if>
        <c:if test="${param.updated == 'true'}">
            <div class="alert alert-success">✅ Đã cập nhật số lượng!</div>
        </c:if>
        <c:if test="${param.removed == 'true'}">
            <div class="alert alert-success">✅ Đã xóa sách khỏi giỏ!</div>
        </c:if>
        <c:if test="${param.cleared == 'true'}">
            <div class="alert alert-success">✅ Đã xóa toàn bộ giỏ hàng!</div>
        </c:if>
        <c:if test="${param.error == 'quantity'}">
            <div class="alert alert-error">❌ Số lượng không hợp lệ hoặc vượt quá tồn kho!</div>
        </c:if>
        <c:if test="${param.error == 'empty'}">
            <div class="alert alert-error">❌ Giỏ hàng trống, không thể thanh toán!</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>

        <!-- Giỏ hàng trống -->
        <c:if test="${empty cartItems}">
            <div style="text-align:center; padding:60px 20px; background:white; border-radius:10px;">
                <h2 style="color:#666;">🛒 Giỏ hàng trống</h2>
                <p style="color:#666; margin:20px 0;">Bạn chưa có sản phẩm nào trong giỏ.</p>
                <a href="${pageContext.request.contextPath}/home" class="btn btn-primary">
                    ← Tiếp tục mua sắm
                </a>
            </div>
        </c:if>

        <!-- Giỏ hàng có sản phẩm -->
        <c:if test="${not empty cartItems}">
            <table class="cart-table">
                <thead>
                    <tr>
                        <th width="80">Ảnh</th>
                        <th>Tiêu đề</th>
                        <th width="130">Giá</th>
                        <th width="200">Số lượng</th>
                        <th width="150">Thành tiền</th>
                        <th width="100">Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${cartItems}" var="item">
                        <tr>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty item.book.coverImage}">
                                        <img src="${pageContext.request.contextPath}/uploads/${item.book.coverImage}"
                                             onerror="this.src='https://via.placeholder.com/60?text=No'">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="https://via.placeholder.com/60?text=No">
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/book-detail?id=${item.book.bookId}"
                                   style="color:#2563eb; text-decoration:none; font-weight:bold;">
                                    ${item.book.title}
                                </a>
                                <div style="color:#666; font-size:13px;">Tồn kho: ${item.book.quantity}</div>
                            </td>
                            <td>
                                <fmt:formatNumber value="${item.book.price}" type="number" groupingUsed="true"/> VNĐ
                            </td>
                            <td>
                                <form action="${pageContext.request.contextPath}/cart" method="post" class="qty-form">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="cartItemId" value="${item.cartItemId}">
                                    <input type="number" name="quantity" value="${item.quantity}" min="1" max="10">
                                    <button type="submit" class="btn btn-warning" style="padding:6px 10px;">🔄</button>
                                </form>
                            </td>
                            <td style="color:#dc2626; font-weight:bold;">
                                <fmt:formatNumber value="${item.book.price * item.quantity}"
                                                  type="number" groupingUsed="true"/> VNĐ
                            </td>
                            <td>
                                <form action="${pageContext.request.contextPath}/cart" method="post"
                                      onsubmit="return confirm('Xóa sách này khỏi giỏ?')">
                                    <input type="hidden" name="action" value="remove">
                                    <input type="hidden" name="cartItemId" value="${item.cartItemId}">
                                    <button type="submit" class="btn btn-danger" style="padding:6px 10px;">🗑️</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <!-- Tổng kết + nút thanh toán -->
            <div class="cart-summary">
                <div>
                    <div style="color:#666;">Tổng cộng (${cartItems.size()} sản phẩm):</div>
                    <div class="total">
                        <fmt:formatNumber value="${totalAmount}" type="number" groupingUsed="true"/> VNĐ
                    </div>
                </div>
                <div class="cart-actions">
                    <form action="${pageContext.request.contextPath}/cart" method="post"
                          onsubmit="return confirm('Xóa toàn bộ giỏ hàng?')">
                        <input type="hidden" name="action" value="clear">
                        <button type="submit" class="btn" style="background:#6b7280; color:white;">
                            🗑️ Xóa toàn bộ
                        </button>
                    </form>
                    <a href="${pageContext.request.contextPath}/checkout" class="btn btn-success">
                        💳 Thanh toán COD
                    </a>
                </div>
            </div>
        </c:if>
    </main>

    <footer class="footer">
        <p><b>Họ tên:</b> Huỳnh Phú Trọng | <b>MSSV:</b> 24162133 | <b>Mã đề:</b> 01</p>
    </footer>

</body>
</html>