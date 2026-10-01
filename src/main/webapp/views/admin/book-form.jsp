<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${mode == 'edit' ? 'Sửa' : 'Thêm'} sách - Admin</title>
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
        <h1>${mode == 'edit' ? '✏️ Sửa sách' : '➕ Thêm sách'}</h1>

        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>

        <div style="background:white; padding:25px; border-radius:10px; max-width:700px;">
            <form action="${pageContext.request.contextPath}/admin/books/${mode == 'edit' ? 'edit' : 'add'}"
                  method="post">

                <c:if test="${mode == 'edit'}">
                    <input type="hidden" name="bookId" value="${book.bookId}">
                </c:if>

                <div class="form-group">
                    <label>Tiêu đề *</label>
                    <input type="text" name="title" required
                           value="${book.title}"
                           placeholder="VD: Mắt biếc">
                </div>

                <div class="form-group">
                    <label>ISBN</label>
                    <input type="number" name="isbn"
                           value="${book.isbn}"
                           placeholder="VD: 1001">
                </div>

                <div class="form-group">
                    <label>Nhà xuất bản</label>
                    <input type="text" name="publisher"
                           value="${book.publisher}"
                           placeholder="VD: NXB Trẻ">
                </div>

                <div class="form-group">
                    <label>Giá (VNĐ)</label>
                    <input type="number" name="price" step="1000"
                           value="${book.price}"
                           placeholder="VD: 120000">
                </div>

                <div class="form-group">
                    <label>Ngày xuất bản</label>
                    <input type="date" name="publishDate"
                           value="${book.publishDate}">
                </div>

                <div class="form-group">
                    <label>Số lượng</label>
                    <input type="number" name="quantity"
                           value="${book.quantity}"
                           placeholder="VD: 10">
                </div>

                <div class="form-group">
                    <label>Tên file ảnh (cover)</label>
                    <input type="text" name="coverImage"
                           value="${book.coverImage}"
                           placeholder="VD: book1.jpg">
                </div>

                <div class="form-group">
                    <label>Mô tả</label>
                    <textarea name="description" rows="3"
                              placeholder="Mô tả ngắn...">${book.description}</textarea>
                </div>

                <div class="form-group">
                    <label>Tác giả * (chọn 1 hoặc nhiều)</label>
                    <select name="authorIds" multiple size="5"
                            style="width:100%; padding:10px; border:1px solid #d1d5db; border-radius:6px;">
                        <c:forEach items="${authors}" var="a">
                            <option value="${a.authorId}"
                                <c:forEach items="${book.authors}" var="ba">
                                    <c:if test="${ba.authorId == a.authorId}">selected</c:if>
                                </c:forEach>>
                                ${a.authorName}
                            </option>
                        </c:forEach>
                    </select>
                    <small style="color:#666;">Giữ Ctrl để chọn nhiều</small>
                </div>

                <div style="margin-top:20px;">
                    <button type="submit" class="btn btn-primary">💾 Lưu</button>
                    <a href="${pageContext.request.contextPath}/admin/books"
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