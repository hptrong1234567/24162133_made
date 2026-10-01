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

@WebServlet(urlPatterns = {"/admin/authors/add"})
public class AuthorAddController_24162133 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final AuthorService_24162133 authorService = new AuthorServiceImpl_24162133();

    // ===== HIỂN THỊ FORM THÊM =====
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("mode", "create");
        req.getRequestDispatcher("/views/admin/author-form.jsp").forward(req, resp);
    }

    // ===== XỬ LÝ THÊM =====
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String authorName = req.getParameter("authorName");
        String dobStr = req.getParameter("dateOfBirth");

        // ===== VALIDATE =====
        if (authorName == null || authorName.trim().isEmpty()) {
            req.setAttribute("error", "Tên tác giả không được để trống");
            req.setAttribute("mode", "create");
            req.getRequestDispatcher("/views/admin/author-form.jsp").forward(req, resp);
            return;
        }

        Author_24162133 author = new Author_24162133();
        author.setAuthorName(authorName.trim());

        if (dobStr != null && !dobStr.isEmpty()) {
            try {
                author.setDateOfBirth(LocalDate.parse(dobStr));
            } catch (Exception e) {
                author.setDateOfBirth(null);
            }
        }

        try {
            authorService.insert(author);
            System.out.println(">>> Thêm author thành công: " + authorName);
            resp.sendRedirect(req.getContextPath() + "/admin/authors?success=add");
        } catch (Exception e) {
            System.out.println(">>> Lỗi thêm author: " + e.getMessage());
            e.printStackTrace();
            req.setAttribute("error", "Lỗi: " + e.getMessage());
            req.setAttribute("mode", "create");
            req.getRequestDispatcher("/views/admin/author-form.jsp").forward(req, resp);
        }
    }
}