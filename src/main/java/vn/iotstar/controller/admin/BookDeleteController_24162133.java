package vn.iotstar.controller.admin;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.service.BookService_24162133;
import vn.iotstar.service.impl.BookServiceImpl_24162133;

@WebServlet(urlPatterns = {"/admin/books/delete"})
public class BookDeleteController_24162133 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final BookService_24162133 bookService = new BookServiceImpl_24162133();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idStr = req.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/admin/books");
            return;
        }

        try {
            Integer id = Integer.parseInt(idStr);
            bookService.delete(id);
            System.out.println(">>> Xóa book thành công id = " + id);
            resp.sendRedirect(req.getContextPath() + "/admin/books?success=delete");
        } catch (Exception e) {
            System.out.println(">>> Lỗi xóa book: " + e.getMessage());
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/admin/books?error=delete");
        }
    }
}