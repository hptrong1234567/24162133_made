package vn.iotstar.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.model.Book_24162133;
import vn.iotstar.model.Rating_24162133;
import vn.iotstar.model.User_24162133;
import vn.iotstar.service.BookService_24162133;
import vn.iotstar.service.RatingService_24162133;
import vn.iotstar.service.impl.BookServiceImpl_24162133;
import vn.iotstar.service.impl.RatingServiceImpl_24162133;
import vn.iotstar.util.Constant_24162133;

@WebServlet(urlPatterns = {"/book-detail"})
public class BookDetailController_24162133 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final BookService_24162133 bookService = new BookServiceImpl_24162133();
    private final RatingService_24162133 ratingService = new RatingServiceImpl_24162133();

    // ============================================================
    // HIỂN THỊ CHI TIẾT SÁCH
    // ============================================================
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String idStr = req.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        Integer bookId;
        try {
            bookId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        Book_24162133 book = bookService.findById(bookId);
        if (book == null) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        List<Rating_24162133> reviews = ratingService.findByBookId(bookId);
        long reviewCount = ratingService.countByBookId(bookId);

        req.setAttribute("book", book);
        req.setAttribute("reviews", reviews);
        req.setAttribute("reviewCount", reviewCount);

        req.getRequestDispatcher("/views/book-detail.jsp").forward(req, resp);
    }

    // ============================================================
    // XỬ LÝ THÊM REVIEW (CÓ LOG TỪNG BƯỚC)
    // ============================================================
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        System.out.println(">>> ========== BẮT ĐẦU doPost /book-detail ==========");

        // ===== LOG PARAMS =====
        String bookIdStr = req.getParameter("bookId");
        String ratingStr = req.getParameter("rating");
        String reviewText = req.getParameter("reviewText");

        System.out.println(">>> bookId = " + bookIdStr);
        System.out.println(">>> rating = " + ratingStr);
        System.out.println(">>> reviewText = " + reviewText);

        // ===== KIỂM TRA LOGIN =====
        User_24162133 user = null;
        try {
            user = (User_24162133) req.getSession()
                    .getAttribute(Constant_24162133.SESSION_USER);
        } catch (Exception e) {
            System.out.println(">>> Lỗi lấy session: " + e.getMessage());
        }

        System.out.println(">>> user = " + (user != null ? user.getEmail() : "NULL"));

        if (user == null) {
            System.out.println(">>> Chưa login → redirect /login");
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // ===== BƯỚC 1: PARSE bookId =====
        System.out.println(">>> Bước 1: parse bookId");
        Integer bookId;
        try {
            bookId = Integer.parseInt(bookIdStr);
            System.out.println(">>>   bookId = " + bookId);
        } catch (Exception e) {
            System.out.println(">>> ❌ Lỗi parse bookId: " + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        // ===== BƯỚC 2: LOAD BOOK TỪ DB =====
        System.out.println(">>> Bước 2: load book từ DB");
        Book_24162133 book = null;
        try {
            book = bookService.findById(bookId);
            System.out.println(">>>   book = " + (book != null ? book.getTitle() : "NULL"));
        } catch (Exception e) {
            System.out.println(">>> ❌ Lỗi load book: " + e.getClass().getName());
            System.out.println(">>>    Message: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        if (book == null) {
            System.out.println(">>> ❌ Không tìm thấy book id = " + bookId);
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        // ===== BƯỚC 3: PARSE rating =====
        System.out.println(">>> Bước 3: parse rating");
        Short rating = 5;
        try {
            rating = Short.parseShort(ratingStr);
            System.out.println(">>>   rating = " + rating);
        } catch (Exception e) {
            System.out.println(">>>   Lỗi parse rating, dùng mặc định = 5");
        }

        // ===== BƯỚC 4: TẠO RATING OBJECT =====
        System.out.println(">>> Bước 4: tạo Rating object");
        Rating_24162133 r = new Rating_24162133();
        r.setUser(user);
        r.setBook(book);
        r.setRating(rating);
        r.setReviewText(reviewText);
        System.out.println(">>>   Rating object đã tạo");

        // ===== BƯỚC 5: INSERT =====
        System.out.println(">>> Bước 5: insert vào DB");
        try {
            ratingService.insert(r);
            System.out.println(">>> ✅ Insert review THÀNH CÔNG!");
        } catch (Exception e) {
            System.out.println(">>> ❌ Insert review LỖI: " + e.getClass().getName());
            System.out.println(">>>    Message: " + e.getMessage());
            e.printStackTrace();

            req.setAttribute("error", "Không thể thêm đánh giá: " + e.getMessage());
            doGet(req, resp);
            return;
        }

        System.out.println(">>> ========== KẾT THÚC doPost ==========");

        // ===== REDIRECT LẠI TRANG CHI TIẾT =====
        resp.sendRedirect(req.getContextPath() + "/book-detail?id=" + bookId);
    }
}