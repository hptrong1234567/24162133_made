package vn.iotstar.dao;

import java.util.List;

import vn.iotstar.model.Order_24162133;

public interface OrderDAO_24162133 {
    List<Order_24162133> findByUserId(Integer userId);
    Order_24162133 findById(Integer orderId);
    Order_24162133 insert(Order_24162133 order);
    void update(Order_24162133 order);
    List<Order_24162133> findAll();
}