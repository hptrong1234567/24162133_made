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
import vn.iotstar.model.Order_24162133;
import vn.iotstar.model.User_24162133;
import vn.iotstar.service.CartService_24162133;
import vn.iotstar.service.OrderService_24162133;
import vn.iotstar.service.impl.CartServiceImpl_24162133;
import vn.iotstar.service.impl.OrderServiceImpl_24162133;
import vn.iotstar.util.Constant_24162133;

@WebServlet(urlPatterns = {"/checkout"})
public class CheckoutController_24162133 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final CartService_24162133 cartService = new CartServiceImpl_24162133();
    private final OrderService_24162133 orderService = new OrderServiceImpl_24162133();

    // ============================================================
    // GET: Hiển thị trang thanh toán (form nhập địa chỉ + SĐT)
    // ============================================================
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        User_24162133 user = (User_24162133) req.getSession()
                .getAttribute(Constant_24162133.SESSION_USER);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Lấy giỏ hàng — nếu trống thì quay về giỏ
        List<CartItem_24162133> cartItems = cartService.getCart(user.getId());
        if (cartItems.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/cart?error=empty");
            return;
        }

        BigDecimal total = cartService.getTotalAmount(user.getId());

        req.setAttribute("cartItems", cartItems);
        req.setAttribute("totalAmount", total);

        req.getRequestDispatcher("/views/checkout.jsp").forward(req, resp);
    }

    // ============================================================
    // POST: Xử lý đặt hàng COD
    // ============================================================
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        User_24162133 user = (User_24162133) req.getSession()
                .getAttribute(Constant_24162133.SESSION_USER);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String shippingAddress = req.getParameter("shippingAddress");
        String phone = req.getParameter("phone");
        String note = req.getParameter("note");
        String paymentMethod = req.getParameter("paymentMethod");   // COD

        // Validate
        if (shippingAddress == null || shippingAddress.trim().isEmpty()) {
            req.setAttribute("error", "Địa chỉ giao hàng không được để trống");
            doGet(req, resp);
            return;
        }

        if (phone == null || phone.trim().isEmpty()) {
            req.setAttribute("error", "Số điện thoại không được để trống");
            doGet(req, resp);
            return;
        }

        // Chỉ chấp nhận COD
        if (paymentMethod == null || !"COD".equals(paymentMethod)) {
            paymentMethod = "COD";
        }

        try {
            Order_24162133 order = orderService.checkoutCOD(
                    user.getId(),
                    shippingAddress.trim(),
                    phone.trim(),
                    note
            );

            System.out.println(">>> Đặt hàng COD thành công. Order ID = " + order.getOrderId());

            // Redirect sang trang xác nhận đơn hàng
            resp.sendRedirect(req.getContextPath()
                    + "/order-history?success=" + order.getOrderId());

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi đặt hàng: " + e.getMessage());
            doGet(req, resp);
        }
    }
}