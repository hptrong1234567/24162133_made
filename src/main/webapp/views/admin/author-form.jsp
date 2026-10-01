<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${mode == 'edit' ? 'Sửa' : 'Thêm'} tác giả - Admin</title>
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
        <h1>${mode == 'edit' ? '✏️ Sửa tác giả' : '➕ Thêm tác giả'}</h1>

        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>

        <div style="background:white; padding:25px; border-radius:10px; max-width:600px;">
            <form action="${pageContext.request.contextPath}/admin/authors/${mode == 'edit' ? 'edit' : 'add'}"
                  method="post">

                <c:if test="${mode == 'edit'}">
                    <input type="hidden" name="authorId" value="${author.authorId}">
                </c:if>

                <div class="form-group">
                    <label>Tên tác giả *</label>
                    <input type="text" name="authorName" required
                           value="${author.authorName}"
                           placeholder="VD: Nguyễn Nhật Ánh">
                </div>

                <div class="form-group">
                    <label>Ngày sinh</label>
                    <input type="date" name="dateOfBirth"
                           value="${author.dateOfBirth}">
                </div>

                <div style="margin-top:20px;">
                    <button type="submit" class="btn btn-primary">💾 Lưu</button>
                    <a href="${pageContext.request.contextPath}/admin/authors"
                       class="btn" style="background:#6b7280; color:white;">Hủy</a>
                </div>
            </form>
        </div>
    </main>

    <footer class="footer">
        <p><b>Họ tên:</b> Huỳnh Phú Trọng | <b>MSSV:</b> 24162133 | <b>Mã đề:</b> 01</p>
    </footer>

</body>
</html>