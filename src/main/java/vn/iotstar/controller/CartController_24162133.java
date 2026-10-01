package vn.iotstar.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.model.CartItem_24162133;
import vn.iotstar.model.User_24162133;
import vn.iotstar.service.CartService_24162133;
import vn.iotstar.service.impl.CartServiceImpl_24162133;
import vn.iotstar.util.Constant_24162133;

@WebServlet(urlPatterns = {"/cart"})
public class CartController_24162133 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final CartService_24162133 cartService = new CartServiceImpl_24162133();

    // ============================================================
    // GET: Hiển thị giỏ hàng
    // ============================================================
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        // Kiểm tra login
        User_24162133 user = (User_24162133) req.getSession()
                .getAttribute(Constant_24162133.SESSION_USER);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Lấy giỏ hàng
        List<CartItem_24162133> cartItems = cartService.getCart(user.getId());
        BigDecimal total = cartService.getTotalAmount(user.getId());

        req.setAttribute("cartItems", cartItems);
        req.setAttribute("totalAmount", total);
        req.setAttribute("cartCount", cartItems.size());

        req.getRequestDispatcher("/views/cart.jsp").forward(req, resp);
    }

    // ============================================================
    // POST: Xử lý các hành động (add, update, remove)
    // ============================================================
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        // Kiểm tra login
        User_24162133 user = (User_24162133) req.getSession()
                .getAttribute(Constant_24162133.SESSION_USER);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String action = req.getParameter("action");

        try {
            if ("add".equals(action)) {
                // ===== THÊM VÀO GIỎ =====
                Integer bookId = Integer.parseInt(req.getParameter("bookId"));
                int quantity = 1;
                try {
                    quantity = Integer.parseInt(req.getParameter("quantity"));
                } catch (Exception ignored) {}

                cartService.addToCart(user.getId(), bookId, quantity);

                // Redirect về trang chi tiết sách kèm thông báo
                resp.sendRedirect(req.getContextPath()
                        + "/book-detail?id=" + bookId + "&added=true");

            } else if ("update".equals(action)) {
                // ===== CẬP NHẬT SỐ LƯỢNG =====
                Integer cartItemId = Integer.parseInt(req.getParameter("cartItemId"));
                int newQty = Integer.parseInt(req.getParameter("quantity"));

                boolean ok = cartService.updateQuantity(cartItemId, newQty);

                if (ok) {
                    resp.sendRedirect(req.getContextPath() + "/cart?updated=true");
                } else {
                    resp.sendRedirect(req.getContextPath() + "/cart?error=quantity");
                }

            } else if ("remove".equals(action)) {
                // ===== XÓA 1 ITEM =====
                Integer cartItemId = Integer.parseInt(req.getParameter("cartItemId"));
                cartService.removeItem(cartItemId);
                resp.sendRedirect(req.getContextPath() + "/cart?removed=true");

            } else if ("clear".equals(action)) {
                // ===== XÓA TOÀN BỘ GIỎ =====
                cartService.clearCart(user.getId());
                resp.sendRedirect(req.getContextPath() + "/cart?cleared=true");

            } else {
                resp.sendRedirect(req.getContextPath() + "/cart");
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi: " + e.getMessage());
            doGet(req, resp);
        }
    }
}