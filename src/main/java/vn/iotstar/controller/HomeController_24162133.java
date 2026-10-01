package vn.iotstar.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.model.Book_24162133;
import vn.iotstar.service.BookService_24162133;
import vn.iotstar.service.impl.BookServiceImpl_24162133;
import vn.iotstar.util.Constant_24162133;

@WebServlet(urlPatterns = {"/home", ""})
public class HomeController_24162133 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final BookService_24162133 bookService = new BookServiceImpl_24162133();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        // ===== LẤY PARAM PAGE =====
        int page = 1;
        String pageStr = req.getParameter("page");
        if (pageStr != null && !pageStr.isEmpty()) {
            try {
                page = Integer.parseInt(pageStr);
                if (page < 1) page = 1;
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        // ===== LẤY SIZE =====
        int size = Constant_24162133.PAGE_SIZE;  // = 6

        // ===== TÍNH TỔNG SỐ TRANG =====
        int totalPages = bookService.getTotalPages(size);
        if (page > totalPages && totalPages > 0) page = totalPages;

        // ===== LẤY DANH SÁCH SÁCH THEO TRANG =====
        List<Book_24162133> books = bookService.findAll(page, size);

        // ===== SET ATTRIBUTES =====
        req.setAttribute("books", books);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("pageSize", size);

        System.out.println(">>> Home: page " + page + "/" + totalPages
                + " - " + books.size() + " sách");

        // ===== FORWARD =====
        req.getRequestDispatcher("/views/home.jsp").forward(req, resp);
    }
}