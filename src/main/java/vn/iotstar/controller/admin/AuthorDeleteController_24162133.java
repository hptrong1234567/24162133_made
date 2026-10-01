package vn.iotstar.controller.admin;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.service.AuthorService_24162133;
import vn.iotstar.service.impl.AuthorServiceImpl_24162133;

@WebServlet(urlPatterns = {"/admin/authors/delete"})
public class AuthorDeleteController_24162133 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final AuthorService_24162133 authorService = new AuthorServiceImpl_24162133();

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
            authorService.delete(id);
            System.out.println(">>> Xóa author thành công id = " + id);
            resp.sendRedirect(req.getContextPath() + "/admin/authors?success=delete");
        } catch (Exception e) {
            System.out.println(">>> Lỗi xóa author: " + e.getMessage());
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/admin/authors?error=delete");
        }
    }
}