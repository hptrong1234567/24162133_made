package vn.iotstar.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.model.Rating_24162133;
import vn.iotstar.service.RatingService_24162133;
import vn.iotstar.service.impl.RatingServiceImpl_24162133;

/**
 * Controller phụ trợ — API JSON lấy reviews của 1 sách.
 * Truy cập: /api/reviews?bookId=1
 */
@WebServlet(urlPatterns = {"/api/reviews"})
public class RatingController_24162133 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final RatingService_24162133 ratingService = new RatingServiceImpl_24162133();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        String bookIdStr = req.getParameter("bookId");
        if (bookIdStr == null || bookIdStr.isEmpty()) {
            resp.getWriter().write("{\"error\":\"bookId is required\"}");
            return;
        }

        Integer bookId;
        try {
            bookId = Integer.parseInt(bookIdStr);
        } catch (NumberFormatException e) {
            resp.getWriter().write("{\"error\":\"bookId must be integer\"}");
            return;
        }

        List<Rating_24162133> list = ratingService.findByBookId(bookId);

        // Build JSON thủ công (không cần thư viện ngoài)
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < list.size(); i++) {
            Rating_24162133 r = list.get(i);
            if (i > 0) sb.append(",");
            sb.append("{");
            sb.append("\"userName\":\"").append(escape(r.getUser().getFullname())).append("\",");
            sb.append("\"rating\":").append(r.getRating()).append(",");
            sb.append("\"reviewText\":\"").append(escape(r.getReviewText())).append("\"");
            sb.append("}");
        }
        sb.append("]");

        resp.getWriter().write(sb.toString());
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"")
                .replace("\n", "\\n").replace("\r", "");
    }
}