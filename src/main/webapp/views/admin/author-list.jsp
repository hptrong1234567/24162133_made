<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý Tác giả - Admin</title>
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
        <h1>👤 Quản lý Tác giả</h1>

        <!-- Thông báo -->
        <c:if test="${param.success == 'add'}">
            <div class="alert alert-success">✅ Thêm tác giả thành công!</div>
        </c:if>
        <c:if test="${param.success == 'edit'}">
            <div class="alert alert-success">✅ Cập nhật tác giả thành công!</div>
        </c:if>
        <c:if test="${param.success == 'delete'}">
            <div class="alert alert-success">✅ Xóa tác giả thành công!</div>
        </c:if>
        <c:if test="${param.error == 'delete'}">
            <div class="alert alert-error">❌ Lỗi xóa tác giả (có thể có sách đang dùng)!</div>
        </c:if>

        <div style="margin-bottom:15px;">
            <a href="${pageContext.request.contextPath}/admin/authors/add"
               class="btn btn-primary">➕ Thêm tác giả</a>
        </div>

        <!-- TABLE -->
        <table>
            <thead>
                <tr>
                    <th width="60">ID</th>
                    <th>Tên tác giả</th>
                    <th width="150">Ngày sinh</th>
                    <th width="200">Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${empty authors}">
                    <tr>
                        <td colspan="4" style="text-align:center;">Không có dữ liệu.</td>
                    </tr>
                </c:if>

                <c:forEach items="${authors}" var="a">
                    <tr>
                        <td>${a.authorId}</td>
                        <td>${a.authorName}</td>
                        <td>${a.dateOfBirth}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/authors/edit?id=${a.authorId}"
                               class="btn btn-warning">✏️ Sửa</a>
                            <a href="${pageContext.request.contextPath}/admin/authors/delete?id=${a.authorId}"
                               class="btn btn-danger"
                               onclick="return confirm('Bạn có chắc muốn xóa tác giả này?')">🗑️ Xóa</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- PHÂN TRANG -->
        <c:if test="${totalPages > 1}">
            <nav class="pagination">
                <c:if test="${currentPage > 1}">
                    <a href="${pageContext.request.contextPath}/admin/authors?page=${currentPage - 1}">« Trước</a>
                </c:if>
                <c:forEach begin="1" end="${totalPages}" var="i">
                    <a href="${pageContext.request.contextPath}/admin/authors?page=${i}"
                       class="${i == currentPage ? 'active' : ''}">${i}</a>
                </c:forEach>
                <c:if test="${currentPage < totalPages}">
                    <a href="${pageContext.request.contextPath}/admin/authors?page=${currentPage + 1}">Sau »</a>
                </c:if>
            </nav>
        </c:if>
    </main>

    <footer class="footer">
        <p><b>Họ tên:</b> Huỳnh Phú Trọng | <b>MSSV:</b> 24162133 | <b>Mã đề:</b> 01</p>
    </footer>

</body>
</html>