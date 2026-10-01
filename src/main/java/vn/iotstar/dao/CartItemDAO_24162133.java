package vn.iotstar.dao;

import java.util.List;

import vn.iotstar.model.CartItem_24162133;

public interface CartItemDAO_24162133 {
    List<CartItem_24162133> findByUserId(Integer userId);
    CartItem_24162133 findByUserAndBook(Integer userId, Integer bookId);
    CartItem_24162133 findById(Integer cartItemId);
    CartItem_24162133 insert(CartItem_24162133 item);
    void update(CartItem_24162133 item);
    void delete(Integer cartItemId);
    void deleteByUserId(Integer userId);
    long countByUserId(Integer userId);
}