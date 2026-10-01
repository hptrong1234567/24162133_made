<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý Sách - Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <!-- HEADER -->
    <header class="header">
        <div class="brand">
            <a href="${pageContext.request.contextPath}/home">📚 BookStore ADMIN</a>
        </div>
        <nav>
            <a href="${pageContext.request.contextPath}/admin/books">Sách</a>
            <a href="${pageContext.request.contextPath}/admin/authors">Tác giả</a>
        </nav>
        <div class="account">
            <span>Admin: <b>${sessionScope.account.fullname}</b></span>
            <a href="${pageContext.request.contextPath}/logout" class="btn-logout">Đăng xuất</a>
        </div>
    </header>

    <main class="container">
        <h1>📖 Quản lý Sách</h1>

        <!-- Thông báo -->
        <c:if test="${param.success == 'add'}">
            <div class="alert alert-success">✅ Thêm sách thành công!</div>
        </c:if>
        <c:if test="${param.success == 'edit'}">
            <div class="alert alert-success">✅ Cập nhật sách thành công!</div>
        </c:if>
        <c:if test="${param.success == 'delete'}">
            <div class="alert alert-success">✅ Xóa sách thành công!</div>
        </c:if>
        <c:if test="${param.error == 'delete'}">
            <div class="alert alert-error">❌ Lỗi xóa sách!</div>
        </c:if>

        <div style="margin-bottom:15px;">
            <a href="${pageContext.request.contextPath}/admin/books/add"
               class="btn btn-primary">➕ Thêm sách</a>
        </div>

        <!-- TABLE -->
        <table>
            <thead>
                <tr>
                    <th width="60">ID</th>
                    <th width="100">Ảnh</th>
                    <th>Tiêu đề</th>
                    <th width="150">Tác giả</th>
                    <th width="130">Giá</th>
                    <th width="80">SL</th>
                    <th width="180">Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${empty books}">
                    <tr>
                        <td colspan="7" style="text-align:center;">Không có dữ liệu.</td>
                    </tr>
                </c:if>

                <c:forEach items="${books}" var="b">
                    <tr>
                        <td>${b.bookId}</td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty b.coverImage}">
                                    <img src="${pageContext.request.contextPath}/uploads/${b.coverImage}"
                                         style="width:60px; height:60px; object-fit:cover;"
                                         onerror="this.src='https://via.placeholder.com/60?text=No'">
                                </c:when>
                                <c:otherwise>
                                    <img src="https://via.placeholder.com/60?text=No"
                                         style="width:60px; height:60px; object-fit:cover;">
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${b.title}</td>
                        <td>
                            <c:forEach items="${b.authors}" var="a" varStatus="st">
                                ${a.authorName}<c:if test="${!st.last}">, </c:if>
                            </c:forEach>
                        </td>
                        <td>
                            <fmt:formatNumber value="${b.price}" type="number" groupingUsed="true"/> VNĐ
                        </td>
                        <td>${b.quantity}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/books/edit?id=${b.bookId}"
                               class="btn btn-warning">✏️ Sửa</a>
                            <a href="${pageContext.request.contextPath}/admin/books/delete?id=${b.bookId}"
                               class="btn btn-danger"
                               onclick="return confirm('Bạn có chắc muốn xóa sách này?')">🗑️ Xóa</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- PHÂN TRANG -->
        <c:if test="${totalPages > 1}">
            <nav class="pagination">
                <c:if test="${currentPage > 1}">
                    <a href="${pageContext.request.contextPath}/admin/books?page=${currentPage - 1}">« Trước</a>
                </c:if>
                <c:forEach begin="1" end="${totalPages}" var="i">
                    <a href="${pageContext.request.contextPath}/admin/books?page=${i}"
                       class="${i == currentPage ? 'active' : ''}">${i}</a>
                </c:forEach>
                <c:if test="${currentPage < totalPages}">
                    <a href="${pageContext.request.contextPath}/admin/books?page=${currentPage + 1}">Sau »</a>
                </c:if>
            </nav>
        </c:if>
    </main>

    <footer class="footer">
        <p><b>Họ tên:</b> Huỳnh Phú Trọng | <b>MSSV:</b> 24162133 | <b>Mã đề:</b> 01</p>
    </footer>

</body>
</html>