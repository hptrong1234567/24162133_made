<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Trang Chủ - BookStore</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .book-grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 20px;
            margin-top: 20px;
        }
        .book-card {
            background: white;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.08);
            overflow: hidden;
            transition: transform 0.2s;
        }
        .book-card:hover {
            transform: translateY(-4px);
            box-shadow: 0 4px 20px rgba(0,0,0,0.15);
        }
        .book-card img {
            width: 100%;
            height: 220px;
            object-fit: cover;
            background: #f1f5f9;
        }
        .book-card .info {
            padding: 15px;
        }
        .book-card .title {
            font-weight: bold;
            font-size: 16px;
            margin-bottom: 8px;
            color: #172554;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
            overflow: hidden;
        }
        .book-card .meta {
            font-size: 13px;
            color: #666;
            margin: 3px 0;
        }
        .book-card .price {
            color: #dc2626;
            font-weight: bold;
            font-size: 18px;
            margin-top: 8px;
        }
        .book-card a {
            text-decoration: none;
            color: inherit;
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
            <a href="${pageContext.request.contextPath}/home">Sản phẩm</a>
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
        <h1 style="color:#172554;">📖 Danh sách sách</h1>
        <p style="color:#666;">Trang ${currentPage} / ${totalPages} (${pageSize} sách/trang)</p>

        <c:if test="${empty books}">
            <div class="alert alert-error">Không có sách nào.</div>
        </c:if>

        <!-- GRID SÁCH -->
        <div class="book-grid">
            <c:forEach items="${books}" var="b">
                <div class="book-card">
                    <a href="${pageContext.request.contextPath}/book-detail?id=${b.bookId}">
                        <c:choose>
                            <c:when test="${not empty b.coverImage}">
                                <img src="${pageContext.request.contextPath}/uploads/${b.coverImage}"
                                     alt="${b.title}"
                                     onerror="this.src='https://via.placeholder.com/300x220?text=No+Image'">
                            </c:when>
                            <c:otherwise>
                                <img src="https://via.placeholder.com/300x220?text=No+Image"
                                     alt="No image">
                            </c:otherwise>
                        </c:choose>

                        <div class="info">
                            <div class="title">${b.title}</div>
                            <div class="meta"><b>ISBN:</b> ${b.isbn}</div>
                            <div class="meta"><b>NXB:</b> ${b.publisher}</div>
                            <div class="meta"><b>Ngày XB:</b> ${b.publishDate}</div>
                            <div class="meta"><b>SL:</b> ${b.quantity}</div>
                            <div class="price">
                                <fmt:formatNumber value="${b.price}" type="number"
                                                  groupingUsed="true"/> VNĐ
                            </div>
                        </div>
                    </a>
                </div>
            </c:forEach>
        </div>

        <!-- PHÂN TRANG -->
        <c:if test="${totalPages > 1}">
            <nav class="pagination">
                <!-- Previous -->
                <c:if test="${currentPage > 1}">
                    <a href="${pageContext.request.contextPath}/home?page=${currentPage - 1}">« Trước</a>
                </c:if>

                <!-- Số trang -->
                <c:forEach begin="1" end="${totalPages}" var="i">
                    <a href="${pageContext.request.contextPath}/home?page=${i}"
                       class="${i == currentPage ? 'active' : ''}">${i}</a>
                </c:forEach>

                <!-- Next -->
                <c:if test="${currentPage < totalPages}">
                    <a href="${pageContext.request.contextPath}/home?page=${currentPage + 1}">Sau »</a>
                </c:if>
            </nav>
        </c:if>
    </main>

    <!-- FOOTER -->
    <footer class="footer">
        <p><b>Họ tên:</b> Huỳnh Phú Trọng</p>
        <p><b>MSSV:</b> 24162133</p>
        <p><b>Mã đề:</b> 01</p>
        <p>© 2026 BookStore - Lập Trình Web</p>
    </footer>

</body>
</html>