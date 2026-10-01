<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đơn hàng của tôi - BookStore</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        /* ===== FILTER BUTTONS ===== */
        .filter-bar {
            display: flex;
            flex-wrap: wrap;
            gap: 8px;
            margin: 20px 0;
            background: white;
            padding: 15px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
        }
        .filter-btn {
            padding: 8px 16px;
            border-radius: 20px;
            text-decoration: none;
            font-size: 14px;
            font-weight: bold;
            border: 2px solid transparent;
            transition: all 0.2s;
            color: #374151;
            background: #f3f4f6;
        }
        .filter-btn:hover {
            background: #e5e7eb;
        }
        .filter-btn.active {
            background: #172554;
            color: white;
            border-color: #172554;
        }

        /* ===== ORDER CARD ===== */
        .order-card {
            background: white;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
            padding: 20px;
            margin-bottom: 20px;
        }
        .order-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding-bottom: 15px;
            border-bottom: 2px solid #f1f5f9;
            margin-bottom: 15px;
        }
        .order-id {
            font-size: 18px;
            color: #172554;
            font-weight: bold;
        }
        .order-status {
            padding: 6px 14px;
            border-radius: 20px;
            font-size: 13px;
            font-weight: bold;
        }

        /* ===== 8 TRẠNG THÁI ===== */
        .status-PENDING    { background: #fef3c7; color: #92400e; }
        .status-CONFIRMED  { background: #dbeafe; color: #1e40af; }
        .status-PREPARING  { background: #ede9fe; color: #5b21b6; }
        .status-SHIPPING   { background: #e0e7ff; color: #4338ca; }
        .status-DELIVERING { background: #ccfbf1; color: #115e59; }
        .status-DELIVERED  { background: #dcfce7; color: #166534; }
        .status-CANCELLED  { background: #fee2e2; color: #991b1b; }
        .status-RETURNED   { background: #ffedd5; color: #9a3412; }

        .order-items-list { list-style: none; }
        .order-items-list li {
            padding: 8px 0;
            border-bottom: 1px solid #f1f5f9;
            display: flex;
            justify-content: space-between;
        }
        .order-footer {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding-top: 15px;
            margin-top: 15px;
            border-top: 2px solid #f1f5f9;
        }
        .order-total { font-size: 20px; color: #dc2626; font-weight: bold; }
        .order-info { color: #666; font-size: 14px; }
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
        <h1>📦 Đơn hàng của tôi</h1>

        <!-- Đặt hàng thành công -->
        <c:if test="${param.success != null}">
            <div class="alert alert-success">
                ✅ Đặt hàng thành công! Mã đơn hàng: <b>#${param.success}</b><br>
                Chúng tôi sẽ liên hệ xác nhận đơn hàng trong thời gian sớm nhất.
            </div>
        </c:if>

        <!-- ===== FILTER BAR ===== -->
        <div class="filter-bar">
            <a href="${pageContext.request.contextPath}/order-history?status=ALL"
               class="filter-btn ${currentStatus == 'ALL' ? 'active' : ''}">
                📋 Tất cả
            </a>
            <a href="${pageContext.request.contextPath}/order-history?status=PENDING"
               class="filter-btn ${currentStatus == 'PENDING' ? 'active' : ''}">
                🆕 Đơn hàng mới
            </a>
            <a href="${pageContext.request.contextPath}/order-history?status=CONFIRMED"
               class="filter-btn ${currentStatus == 'CONFIRMED' ? 'active' : ''}">
                ✅ Đã xác nhận
            </a>
            <a href="${pageContext.request.contextPath}/order-history?status=PREPARING"
               class="filter-btn ${currentStatus == 'PREPARING' ? 'active' : ''}">
                📦 Chuẩn bị hàng
            </a>
            <a href="${pageContext.request.contextPath}/order-history?status=SHIPPING"
               class="filter-btn ${currentStatus == 'SHIPPING' ? 'active' : ''}">
                🚚 Đang vận chuyển
            </a>
            <a href="${pageContext.request.contextPath}/order-history?status=DELIVERING"
               class="filter-btn ${currentStatus == 'DELIVERING' ? 'active' : ''}">
                🛵 Đang giao hàng
            </a>
            <a href="${pageContext.request.contextPath}/order-history?status=DELIVERED"
               class="filter-btn ${currentStatus == 'DELIVERED' ? 'active' : ''}">
                📬 Đã giao hàng
            </a>
            <a href="${pageContext.request.contextPath}/order-history?status=CANCELLED"
               class="filter-btn ${currentStatus == 'CANCELLED' ? 'active' : ''}">
                ❌ Đơn hàng hủy
            </a>
            <a href="${pageContext.request.contextPath}/order-history?status=RETURNED"
               class="filter-btn ${currentStatus == 'RETURNED' ? 'active' : ''}">
                ↩️ Đơn hàng hoàn
            </a>
        </div>

        <!-- Chưa có đơn -->
        <c:if test="${empty orders}">
            <div style="text-align:center; padding:60px 20px; background:white; border-radius:10px;">
                <h2 style="color:#666;">📦 Không có đơn hàng nào</h2>
                <p style="color:#666; margin:20px 0;">
                    <c:choose>
                        <c:when test="${currentStatus == 'ALL'}">Bạn chưa đặt đơn hàng nào.</c:when>
                        <c:otherwise>Không có đơn hàng nào ở trạng thái này.</c:otherwise>
                    </c:choose>
                </p>
                <a href="${pageContext.request.contextPath}/home" class="btn btn-primary">
                    ← Mua sắm ngay
                </a>
            </div>
        </c:if>

        <!-- Danh sách đơn hàng -->
        <c:forEach items="${orders}" var="order">
            <div class="order-card">
                <div class="order-header">
                    <div>
                        <div class="order-id">Đơn hàng #${order.orderId}</div>
                        <div class="order-info">
                            📅 Ngày đặt: ${order.orderDate}<br>
                            💳 Thanh toán: <b>${order.paymentMethod}</b>
                        </div>
                    </div>
                    <div class="order-status status-${order.status}">
                        <c:choose>
                            <c:when test="${order.status == 'PENDING'}">🆕 Đơn hàng mới</c:when>
                            <c:when test="${order.status == 'CONFIRMED'}">✅ Đã xác nhận</c:when>
                            <c:when test="${order.status == 'PREPARING'}">📦 Chuẩn bị hàng</c:when>
                            <c:when test="${order.status == 'SHIPPING'}">🚚 Đang vận chuyển</c:when>
                            <c:when test="${order.status == 'DELIVERING'}">🛵 Đang giao hàng</c:when>
                            <c:when test="${order.status == 'DELIVERED'}">📬 Đã giao hàng</c:when>
                            <c:when test="${order.status == 'CANCELLED'}">❌ Đơn hàng hủy</c:when>
                            <c:when test="${order.status == 'RETURNED'}">↩️ Đơn hàng hoàn</c:when>
                            <c:otherwise>${order.status}</c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <!-- Sản phẩm trong đơn -->
                <ul class="order-items-list">
                    <c:forEach items="${order.items}" var="item">
                        <li>
                            <span>
                                <a href="${pageContext.request.contextPath}/book-detail?id=${item.book.bookId}"
                                   style="color:#2563eb; text-decoration:none;">
                                    ${item.book.title}
                                </a>
                                <span style="color:#666;">× ${item.quantity}</span>
                            </span>
                            <span style="color:#dc2626; font-weight:bold;">
                                <fmt:formatNumber value="${item.price * item.quantity}"
                                                  type="number" groupingUsed="true"/> VNĐ
                            </span>
                        </li>
                    </c:forEach>
                </ul>

                <div class="order-footer">
                    <div class="order-info">
                        📍 <b>Giao đến:</b> ${order.shippingAddress}<br>
                        📞 <b>SĐT:</b> ${order.phone}
                        <c:if test="${not empty order.note}">
                            <br>📝 <b>Ghi chú:</b> ${order.note}
                        </c:if>
                    </div>
                    <div class="order-total">
                        <fmt:formatNumber value="${order.totalAmount}" type="number" groupingUsed="true"/> VNĐ
                    </div>
                </div>
            </div>
        </c:forEach>
    </main>

    <footer class="footer">
        <p><b>Họ tên:</b> Huỳnh Phú Trọng | <b>MSSV:</b> 24162133 | <b>Mã đề:</b> 01</p>
    </footer>

</body>
</html>