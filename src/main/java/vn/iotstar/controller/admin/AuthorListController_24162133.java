package vn.iotstar.controller.admin;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.model.Author_24162133;
import vn.iotstar.service.AuthorService_24162133;
import vn.iotstar.service.impl.AuthorServiceImpl_24162133;

@WebServlet(urlPatterns = {"/admin/authors"})
public class AuthorListController_24162133 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final AuthorService_24162133 authorService = new AuthorServiceImpl_24162133();

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

        int size = 6;

        // ===== TÍNH TỔNG TRANG =====
        int totalPages = authorService.getTotalPages(size);
        if (page > totalPages && totalPages > 0) page = totalPages;

        // ===== LẤY DANH SÁCH =====
        List<Author_24162133> authors = authorService.findAll(page, size);

        req.setAttribute("authors", authors);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);

        System.out.println(">>> Author List: page " + page + "/" + totalPages
                + " - " + authors.size() + " tác giả");

        req.getRequestDispatcher("/views/admin/author-list.jsp").forward(req, resp);
    }
}