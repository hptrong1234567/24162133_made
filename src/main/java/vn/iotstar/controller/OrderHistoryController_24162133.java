package vn.iotstar.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.model.Order_24162133;
import vn.iotstar.model.User_24162133;
import vn.iotstar.service.OrderService_24162133;
import vn.iotstar.service.impl.OrderServiceImpl_24162133;
import vn.iotstar.util.Constant_24162133;

@WebServlet(urlPatterns = {"/order-history"})
public class OrderHistoryController_24162133 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final OrderService_24162133 orderService = new OrderServiceImpl_24162133();

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

        String status = req.getParameter("status");
        if (status == null || status.trim().isEmpty()) {
            status = "ALL";
        }

        List<Order_24162133> orders = orderService.getOrdersByUserAndStatus(user.getId(), status);

        req.setAttribute("orders", orders);
        req.setAttribute("currentStatus", status);

        req.getRequestDispatcher("/views/order-history.jsp").forward(req, resp);
    }
}