<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Admin - <sitemesh:title/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="/common/header.jsp" %>
    
    <div class="admin-layout">
        <aside class="sidebar">
            <h3>Quản trị</h3>
            <ul>
                <li><a href="${pageContext.request.contextPath}/admin/books">Quản lý sách</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/authors">Quản lý tác giả</a></li>
            </ul>
        </aside>
        
        <main class="content">
            <sitemesh:body/>
        </main>
    </div>
    
    <%@ include file="/common/footer.jsp" %>
</body>
</html>