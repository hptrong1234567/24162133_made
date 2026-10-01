package vn.iotstar.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import vn.iotstar.dao.OrderDAO_24162133;
import vn.iotstar.dao.UserDAO_24162133;
import vn.iotstar.dao.impl.OrderDAOImpl_24162133;
import vn.iotstar.dao.impl.UserDAOImpl_24162133;
import vn.iotstar.model.Book_24162133;
import vn.iotstar.model.CartItem_24162133;
import vn.iotstar.model.Order_24162133;
import vn.iotstar.model.OrderItem_24162133;
import vn.iotstar.model.User_24162133;
import vn.iotstar.service.BookService_24162133;
import vn.iotstar.service.CartService_24162133;
import vn.iotstar.service.OrderService_24162133;

public class OrderServiceImpl_24162133 implements OrderService_24162133 {

    private final OrderDAO_24162133 orderDAO = new OrderDAOImpl_24162133();
    private final UserDAO_24162133 userDAO = new UserDAOImpl_24162133();
    private final CartService_24162133 cartService = new CartServiceImpl_24162133();
    private final BookService_24162133 bookService = new BookServiceImpl_24162133();

    // ============================================================
    // ĐẶT HÀNG COD TỪ GIỎ HÀNG
    // ============================================================
    @Override
    public Order_24162133 checkoutCOD(Integer userId, String shippingAddress, String phone, String note) {

        User_24162133 user = userDAO.findById(userId);
        if (user == null) {
            throw new RuntimeException("User không tồn tại");
        }

        List<CartItem_24162133> cartItems = cartService.getCart(userId);
        if (cartItems == null || cartItems.isEmpty()) {
            throw new RuntimeException("Giỏ hàng trống, không thể đặt hàng");
        }

        if (shippingAddress == null || shippingAddress.trim().isEmpty()) {
            throw new RuntimeException("Địa chỉ giao hàng không được để trống");
        }
        if (phone == null || phone.trim().isEmpty()) {
            throw new RuntimeException("Số điện thoại không được để trống");
        }

        Order_24162133 order = new Order_24162133();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setPaymentMethod("COD");
        order.setStatus("PENDING");
        order.setShippingAddress(shippingAddress.trim());
        order.setPhone(phone.trim());
        order.setNote(note);

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItem_24162133 cartItem : cartItems) {
            Book_24162133 book = cartItem.getBook();
            if (book == null) continue;

            int qty = cartItem.getQuantity();
            int stock = (book.getQuantity() == null) ? 0 : book.getQuantity();

            if (stock < qty) {
                throw new RuntimeException("Sách '" + book.getTitle()
                        + "' chỉ còn " + stock + " cuốn, không đủ số lượng đặt");
            }

            BigDecimal price = (book.getPrice() == null) ? BigDecimal.ZERO : book.getPrice();
            OrderItem_24162133 orderItem = new OrderItem_24162133(book, qty, price);
            order.addItem(orderItem);

            totalAmount = totalAmount.add(price.multiply(BigDecimal.valueOf(qty)));

            book.setQuantity(stock - qty);
            bookService.update(book);
        }

        order.setTotalAmount(totalAmount);

        Order_24162133 savedOrder = orderDAO.insert(order);
        cartService.clearCart(userId);

        System.out.println(">>> Đặt hàng COD thành công. Order ID = " + savedOrder.getOrderId());

        return savedOrder;
    }

    // ============================================================
    // LỊCH SỬ ĐƠN HÀNG CỦA USER
    // ============================================================
    @Override
    public List<Order_24162133> getOrdersByUser(Integer userId) {
        return orderDAO.findByUserId(userId);
    }

    // ============================================================
    // LỌC ĐƠN HÀNG THEO TRẠNG THÁI
    // ============================================================
    @Override
    public List<Order_24162133> getOrdersByUserAndStatus(Integer userId, String status) {
        if (status == null || status.trim().isEmpty() || "ALL".equals(status)) {
            return orderDAO.findByUserId(userId);
        }
        return orderDAO.findByUserIdAndStatus(userId, status);
    }

    // ============================================================
    // CHI TIẾT 1 ĐƠN HÀNG
    // ============================================================
    @Override
    public Order_24162133 getOrderById(Integer orderId) {
        return orderDAO.findById(orderId);
    }

    // ============================================================
    // (ADMIN) LẤY TẤT CẢ ĐƠN HÀNG
    // ============================================================
    @Override
    public List<Order_24162133> getAllOrders() {
        return orderDAO.findAll();
    }

    // ============================================================
    // (ADMIN) ĐỔI TRẠNG THÁI ĐƠN HÀNG
    // ============================================================
    @Override
    public void updateStatus(Integer orderId, String newStatus) {
        Order_24162133 order = orderDAO.findById(orderId);
        if (order == null) {
            throw new RuntimeException("Đơn hàng không tồn tại");
        }

        String[] validStatuses = {
                "PENDING", "CONFIRMED", "PREPARING", "SHIPPING",
                "DELIVERING", "DELIVERED", "CANCELLED", "RETURNED"
        };
        boolean valid = false;
        for (String s : validStatuses) {
            if (s.equals(newStatus)) {
                valid = true;
                break;
            }
        }
        if (!valid) {
            throw new RuntimeException("Trạng thái không hợp lệ: " + newStatus);
        }

        order.setStatus(newStatus);
        orderDAO.update(order);

        System.out.println(">>> Cập nhật đơn hàng #" + orderId + " → " + newStatus);
    }
}