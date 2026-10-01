package vn.iotstar.service;

import java.util.List;

import vn.iotstar.model.Order_24162133;

public interface OrderService_24162133 {

    /** Đặt hàng COD từ giỏ hàng */
    Order_24162133 checkoutCOD(Integer userId, String shippingAddress, String phone, String note);

    /** Lịch sử đơn hàng của user */
    List<Order_24162133> getOrdersByUser(Integer userId);

    /** Chi tiết 1 đơn hàng */
    Order_24162133 getOrderById(Integer orderId);

    /** (Admin) Tất cả đơn hàng */
    List<Order_24162133> getAllOrders();

    /** (Admin) Đổi trạng thái đơn */
    void updateStatus(Integer orderId, String newStatus);
}