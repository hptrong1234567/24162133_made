package vn.iotstar.controller.admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.model.Author_24162133;
import vn.iotstar.model.Book_24162133;
import vn.iotstar.service.AuthorService_24162133;
import vn.iotstar.service.BookService_24162133;
import vn.iotstar.service.impl.AuthorServiceImpl_24162133;
import vn.iotstar.service.impl.BookServiceImpl_24162133;

@WebServlet(urlPatterns = {"/admin/books/edit"})
public class BookEditController_24162133 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final BookService_24162133 bookService = new BookServiceImpl_24162133();
    private final AuthorService_24162133 authorService = new AuthorServiceImpl_24162133();

    // ===== HIỂN THỊ FORM SỬA =====
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
            Book_24162133 book = bookService.findById(id);

            if (book == null) {
                resp.sendRedirect(req.getContextPath() + "/admin/books");
                return;
            }

            req.setAttribute("book", book);
            req.setAttribute("authors", authorService.findAll());
            req.setAttribute("mode", "edit");
            req.getRequestDispatcher("/views/admin/book-form.jsp").forward(req, resp);

        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/admin/books");
        }
    }

    // ===== XỬ LÝ CẬP NHẬT =====
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String bookIdStr = req.getParameter("bookId");
        String isbnStr = req.getParameter("isbn");
        String title = req.getParameter("title");
        String publisher = req.getParameter("publisher");
        String priceStr = req.getParameter("price");
        String description = req.getParameter("description");
        String publishDateStr = req.getParameter("publishDate");
        String coverImage = req.getParameter("coverImage");
        String quantityStr = req.getParameter("quantity");
        String[] authorIds = req.getParameterValues("authorIds");

        Integer bookId;
        try {
            bookId = Integer.parseInt(bookIdStr);
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/admin/books");
            return;
        }

        Book_24162133 book = bookService.findById(bookId);
        if (book == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/books");
            return;
        }

        // ===== VALIDATE =====
        if (title == null || title.trim().isEmpty()) {
            req.setAttribute("error", "Tiêu đề không được để trống");
            req.setAttribute("book", book);
            req.setAttribute("authors", authorService.findAll());
            req.setAttribute("mode", "edit");
            req.getRequestDispatcher("/views/admin/book-form.jsp").forward(req, resp);
            return;
        }

        // ===== CẬP NHẬT =====
        book.setTitle(title.trim());
        book.setPublisher(publisher);
        book.setDescription(description);
        book.setCoverImage(coverImage);

        try {
            if (isbnStr != null && !isbnStr.isEmpty())
                book.setIsbn(Integer.parseInt(isbnStr));
        } catch (Exception ignored) {}

        try {
            if (priceStr != null && !priceStr.isEmpty())
                book.setPrice(new BigDecimal(priceStr));
        } catch (Exception ignored) {}

        try {
            if (publishDateStr != null && !publishDateStr.isEmpty())
                book.setPublishDate(LocalDate.parse(publishDateStr));
        } catch (Exception ignored) {}

        try {
            if (quantityStr != null && !quantityStr.isEmpty())
                book.setQuantity(Integer.parseInt(quantityStr));
        } catch (Exception ignored) {}

        // Cập nhật authors
        if (authorIds != null && authorIds.length > 0) {
            List<Author_24162133> selectedAuthors = new ArrayList<>();
            for (String aid : authorIds) {
                try {
                    Author_24162133 a = authorService.findById(Integer.parseInt(aid));
                    if (a != null) selectedAuthors.add(a);
                } catch (Exception ignored) {}
            }
            book.setAuthors(selectedAuthors);
        }

        try {
            bookService.update(book);
            System.out.println(">>> Cập nhật book thành công: " + title);
            resp.sendRedirect(req.getContextPath() + "/admin/books?success=edit");
        } catch (Exception e) {
            System.out.println(">>> Lỗi cập nhật book: " + e.getMessage());
            e.printStackTrace();
            req.setAttribute("error", "Lỗi: " + e.getMessage());
            req.setAttribute("book", book);
            req.setAttribute("authors", authorService.findAll());
            req.setAttribute("mode", "edit");
            req.getRequestDispatcher("/views/admin/book-form.jsp").forward(req, resp);
        }
    }
}