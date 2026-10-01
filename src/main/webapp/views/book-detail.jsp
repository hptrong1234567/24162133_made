<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${book.title} - BookStore</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .book-detail {
            display: grid;
            grid-template-columns: 300px 1fr;
            gap: 30px;
            background: white;
            padding: 25px;
            border-radius: 12px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.08);
        }
        .book-detail img {
            width: 100%;
            border-radius: 10px;
            background: #f1f5f9;
        }
        .book-info h1 {
            color: #172554;
            margin-bottom: 15px;
        }
        .book-info .row {
            padding: 8px 0;
            border-bottom: 1px solid #f1f5f9;
        }
        .book-info .label {
            font-weight: bold;
            display: inline-block;
            width: 130px;
            color: #374151;
        }
        .book-info .price {
            color: #dc2626;
            font-size: 24px;
            font-weight: bold;
            margin: 15px 0;
        }
        .reviews-section {
            background: white;
            padding: 25px;
            border-radius: 12px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.08);
            margin-top: 25px;
        }
        .reviews-section h2 {
            color: #172554;
            margin-bottom: 15px;
        }
        .review-item {
            padding: 15px;
            border-bottom: 1px solid #f1f5f9;
        }
        .review-item:last-child {
            border-bottom: none;
        }
        .review-item .user {
            font-weight: bold;
            color: #2563eb;
        }
        .review-item .rating {
            color: #f59e0b;
        }
        .review-item .text {
            margin-top: 5px;
            color: #374151;
        }
        .review-form {
            background: #f9fafb;
            padding: 20px;
            border-radius: 10px;
            margin-top: 20px;
        }
        .review-form h3 {
            color: #172554;
            margin-bottom: 15px;
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

    <!-- CONTENT -->
    <main class="container">

        <p style="margin-bottom:15px;">
            <a href="${pageContext.request.contextPath}/home">← Quay lại danh sách</a>
        </p>

        <div class="book-detail">
            <div>
                <c:choose>
                    <c:when test="${not empty book.coverImage}">
                        <img src="${pageContext.request.contextPath}/uploads/${book.coverImage}"
                             alt="${book.title}"
                             onerror="this.src='https://via.placeholder.com/300x400?text=No+Image'">
                    </c:when>
                    <c:otherwise>
                        <img src="https://via.placeholder.com/300x400?text=No+Image" alt="No image">
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="book-info">
                <h1>${book.title}</h1>

                <div class="row">
                    <span class="label">Mã ISBN:</span>
                    <span>${book.isbn}</span>
                </div>

                <div class="row">
                    <span class="label">Tác giả:</span>
                    <span>
                        <c:forEach items="${book.authors}" var="a" varStatus="st">
                            ${a.authorName}<c:if test="${!st.last}">, </c:if>
                        </c:forEach>
                    </span>
                </div>

                <div class="row">
                    <span class="label">Publisher:</span>
                    <span>${book.publisher}</span>
                </div>

                <div class="row">
                    <span class="label">Publisher date:</span>
                    <span>${book.publishDate}</span>
                </div>

                <div class="row">
                    <span class="label">Quantity:</span>
                    <span>${book.quantity}</span>
                </div>

                <div class="row">
                    <span class="label">Mô tả:</span>
                    <span>${book.description}</span>
                </div>

                <div class="price">
                    <fmt:formatNumber value="${book.price}" type="number" groupingUsed="true"/> VNĐ
                </div>

                <!-- ===== NÚT THÊM VÀO GIỎ ===== -->
                <div style="margin-top:20px;">
                    <c:choose>
                        <c:when test="${sessionScope.account == null}">
                            <a href="${pageContext.request.contextPath}/login" 
                               class="btn btn-primary" style="padding:12px 24px; font-size:16px;">
                                🔑 Đăng nhập để mua hàng
                            </a>
                        </c:when>
                        <c:when test="${book.quantity == null or book.quantity <= 0}">
                            <button class="btn" disabled 
                                    style="padding:12px 24px; font-size:16px; background:#9ca3af; color:white; cursor:not-allowed;">
                                ❌ Hết hàng
                            </button>
                        </c:when>
                        <c:otherwise>
                            <form action="${pageContext.request.contextPath}/cart" method="post" 
                                  style="display:inline-flex; gap:10px; align-items:center;">
                                <input type="hidden" name="action" value="add">
                                <input type="hidden" name="bookId" value="${book.bookId}">
                                
                                <label style="margin:0; color:#374151; font-weight:bold;">Số lượng:</label>
                                <input type="number" name="quantity" value="1" min="1" max="${book.quantity}" 
                                       style="width:80px; padding:8px; border:1px solid #d1d5db; border-radius:6px; text-align:center;">
                                
                                <button type="submit" class="btn btn-success" 
                                        style="padding:12px 24px; font-size:16px;">
                                    🛒 Thêm vào giỏ hàng
                                </button>
                            </form>
                        </c:otherwise>
                    </c:choose>
                </div>

                <c:if test="${param.added == 'true'}">
                    <div class="alert alert-success" style="margin-top:15px;">
                        ✅ Đã thêm vào giỏ hàng! 
                        <a href="${pageContext.request.contextPath}/cart" style="color:#166534; font-weight:bold;">
                            Xem giỏ hàng →
                        </a>
                    </div>
                </c:if>
            </div>
        </div>

        <!-- REVIEWS -->
        <div class="reviews-section">
            <h2>⭐ Reviews (${reviewCount})</h2>

            <c:if test="${empty reviews}">
                <p style="color:#666;">Chưa có đánh giá nào. Hãy là người đầu tiên!</p>
            </c:if>

            <c:forEach items="${reviews}" var="r">
                <div class="review-item">
                    <div>
                        <span class="user">${r.user.fullname}</span>
                        <span class="rating">
                            <c:forEach begin="1" end="${r.rating}">⭐</c:forEach>
                        </span>
                    </div>
                    <div class="text">${r.reviewText}</div>
                </div>
            </c:forEach>

            <!-- FORM THÊM REVIEW -->
            <div class="review-form">
                <h3>✍️ Thêm đánh giá</h3>

                <c:choose>
                    <c:when test="${sessionScope.account == null}">
                        <p style="color:#dc2626;">
                            Vui lòng <a href="${pageContext.request.contextPath}/login">đăng nhập</a>
                            để viết đánh giá.
                        </p>
                    </c:when>
                    <c:otherwise>
                        <form action="${pageContext.request.contextPath}/book-detail" method="post">
                            <input type="hidden" name="bookId" value="${book.bookId}">

                            <div class="form-group">
                                <label>Đánh giá (1-5 ⭐)</label>
                                <select name="rating" required>
                                    <option value="5">5 ⭐ - Rất hay</option>
                                    <option value="4">4 ⭐ - Hay</option>
                                    <option value="3">3 ⭐ - Bình thường</option>
                                    <option value="2">2 ⭐ - Tệ</option>
                                    <option value="1">1 ⭐ - Rất tệ</option>
                                </select>
                            </div>

                            <div class="form-group">
                                <label>Nội dung đánh giá</label>
                                <textarea name="reviewText" rows="3"
                                          placeholder="Viết cảm nhận của bạn..."
                                          required></textarea>
                            </div>

                            <button type="submit" class="btn btn-primary">
                                📩 Gửi đánh giá
                            </button>
                        </form>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

    </main>

    <footer class="footer">
        <p><b>Họ tên:</b> Huỳnh Phú Trọng | <b>MSSV:</b> 24162133 | <b>Mã đề:</b> 01</p>
    </footer>

</body>
</html>