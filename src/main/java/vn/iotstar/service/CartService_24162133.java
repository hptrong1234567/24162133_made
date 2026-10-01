package vn.iotstar.service;

import java.util.List;

import vn.iotstar.model.CartItem_24162133;

public interface CartService_24162133 {

    /** Lấy giỏ hàng của user */
    List<CartItem_24162133> getCart(Integer userId);

    /** Thêm sách vào giỏ (nếu đã có thì cộng thêm số lượng) */
    void addToCart(Integer userId, Integer bookId, int quantity);

    /** Cập nhật số lượng 1 item — có kiểm tra giới hạn tồn kho */
    boolean updateQuantity(Integer cartItemId, int newQuantity);

    /** Xóa 1 item khỏi giỏ */
    void removeItem(Integer cartItemId);

    /** Xóa toàn bộ giỏ của user */
    void clearCart(Integer userId);

    /** Đếm số item trong giỏ */
    long countItems(Integer userId);

    /** Tổng tiền giỏ hàng */
    java.math.BigDecimal getTotalAmount(Integer userId);
}