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

@WebServlet(urlPatterns = {"/admin/books/add"})
public class BookAddController_24162133 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final BookService_24162133 bookService = new BookServiceImpl_24162133();
    private final AuthorService_24162133 authorService = new AuthorServiceImpl_24162133();

    // ===== HIỂN THỊ FORM THÊM =====
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Lấy danh sách tác giả để chọn
        List<Author_24162133> authors = authorService.findAll();
        req.setAttribute("authors", authors);
        req.setAttribute("mode", "create");

        req.getRequestDispatcher("/views/admin/book-form.jsp").forward(req, resp);
    }

    // ===== XỬ LÝ THÊM =====
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        // ===== LẤY PARAMS =====
        String isbnStr = req.getParameter("isbn");
        String title = req.getParameter("title");
        String publisher = req.getParameter("publisher");
        String priceStr = req.getParameter("price");
        String description = req.getParameter("description");
        String publishDateStr = req.getParameter("publishDate");
        String coverImage = req.getParameter("coverImage");
        String quantityStr = req.getParameter("quantity");
        String[] authorIds = req.getParameterValues("authorIds");

        // ===== VALIDATE =====
        String error = null;
        if (title == null || title.trim().isEmpty()) {
            error = "Tiêu đề không được để trống";
        } else if (authorIds == null || authorIds.length == 0) {
            error = "Phải chọn ít nhất 1 tác giả";
        }

        if (error != null) {
            req.setAttribute("error", error);
            req.setAttribute("authors", authorService.findAll());
            req.setAttribute("mode", "create");
            req.getRequestDispatcher("/views/admin/book-form.jsp").forward(req, resp);
            return;
        }

        // ===== TẠO BOOK =====
        Book_24162133 book = new Book_24162133();
        book.setTitle(title.trim());
        book.setPublisher(publisher);
        book.setDescription(description);
        book.setCoverImage(coverImage);

        // ISBN
        try {
            if (isbnStr != null && !isbnStr.isEmpty())
                book.setIsbn(Integer.parseInt(isbnStr));
        } catch (Exception ignored) {}

        // Price
        try {
            if (priceStr != null && !priceStr.isEmpty())
                book.setPrice(new BigDecimal(priceStr));
            else
                book.setPrice(BigDecimal.ZERO);
        } catch (Exception e) {
            book.setPrice(BigDecimal.ZERO);
        }

        // Publish date
        try {
            if (publishDateStr != null && !publishDateStr.isEmpty())
                book.setPublishDate(LocalDate.parse(publishDateStr));
        } catch (Exception ignored) {}

        // Quantity
        try {
            if (quantityStr != null && !quantityStr.isEmpty())
                book.setQuantity(Integer.parseInt(quantityStr));
            else
                book.setQuantity(0);
        } catch (Exception e) {
            book.setQuantity(0);
        }

        // ===== TÌM AUTHORS =====
        List<Author_24162133> selectedAuthors = new ArrayList<>();
        for (String aid : authorIds) {
            try {
                Author_24162133 a = authorService.findById(Integer.parseInt(aid));
                if (a != null) selectedAuthors.add(a);
            } catch (Exception ignored) {}
        }
        book.setAuthors(selectedAuthors);

        // ===== INSERT =====
        try {
            bookService.insert(book);
            System.out.println(">>> Thêm book thành công: " + title);
            resp.sendRedirect(req.getContextPath() + "/admin/books?success=add");
        } catch (Exception e) {
            System.out.println(">>> Lỗi thêm book: " + e.getMessage());
            e.printStackTrace();
            req.setAttribute("error", "Lỗi: " + e.getMessage());
            req.setAttribute("authors", authorService.findAll());
            req.setAttribute("mode", "create");
            req.getRequestDispatcher("/views/admin/book-form.jsp").forward(req, resp);
        }
    }
}