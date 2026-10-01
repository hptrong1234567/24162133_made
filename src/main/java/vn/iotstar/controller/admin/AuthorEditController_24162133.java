package vn.iotstar.controller.admin;

import java.io.IOException;
import java.time.LocalDate;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.model.Author_24162133;
import vn.iotstar.service.AuthorService_24162133;
import vn.iotstar.service.impl.AuthorServiceImpl_24162133;

@WebServlet(urlPatterns = {"/admin/authors/edit"})
public class AuthorEditController_24162133 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final AuthorService_24162133 authorService = new AuthorServiceImpl_24162133();

    // ===== HIỂN THỊ FORM SỬA =====
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idStr = req.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/admin/authors");
            return;
        }

        try {
            Integer id = Integer.parseInt(idStr);
            Author_24162133 author = authorService.findById(id);

            if (author == null) {
                resp.sendRedirect(req.getContextPath() + "/admin/authors");
                return;
            }

            req.setAttribute("author", author);
            req.setAttribute("mode", "edit");
            req.getRequestDispatcher("/views/admin/author-form.jsp").forward(req, resp);

        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/admin/authors");
        }
    }

    // ===== XỬ LÝ CẬP NHẬT =====
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String idStr = req.getParameter("authorId");
        String authorName = req.getParameter("authorName");
        String dobStr = req.getParameter("dateOfBirth");

        Integer id;
        try {
            id = Integer.parseInt(idStr);
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/admin/authors");
            return;
        }

        Author_24162133 author = authorService.findById(id);
        if (author == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/authors");
            return;
        }

        if (authorName == null || authorName.trim().isEmpty()) {
            req.setAttribute("error", "Tên tác giả không được để trống");
            req.setAttribute("author", author);
            req.setAttribute("mode", "edit");
            req.getRequestDispatcher("/views/admin/author-form.jsp").forward(req, resp);
            return;
        }

        author.setAuthorName(authorName.trim());
        if (dobStr != null && !dobStr.isEmpty()) {
            try {
                author.setDateOfBirth(LocalDate.parse(dobStr));
            } catch (Exception e) {
                author.setDateOfBirth(null);
            }
        }

        try {
            authorService.update(author);
            System.out.println(">>> Cập nhật author thành công: " + authorName);
            resp.sendRedirect(req.getContextPath() + "/admin/authors?success=edit");
        } catch (Exception e) {
            System.out.println(">>> Lỗi cập nhật author: " + e.getMessage());
            e.printStackTrace();
            req.setAttribute("error", "Lỗi: " + e.getMessage());
            req.setAttribute("author", author);
            req.setAttribute("mode", "edit");
            req.getRequestDispatcher("/views/admin/author-form.jsp").forward(req, resp);
        }
    }
}