package vn.iotstar.service.impl;

import java.math.BigDecimal;
import java.util.List;

import vn.iotstar.dao.CartItemDAO_24162133;
import vn.iotstar.dao.UserDAO_24162133;
import vn.iotstar.dao.impl.CartItemDAOImpl_24162133;
import vn.iotstar.dao.impl.UserDAOImpl_24162133;
import vn.iotstar.model.Book_24162133;
import vn.iotstar.model.CartItem_24162133;
import vn.iotstar.model.User_24162133;
import vn.iotstar.service.BookService_24162133;
import vn.iotstar.service.CartService_24162133;
import vn.iotstar.service.impl.BookServiceImpl_24162133;

public class CartServiceImpl_24162133 implements CartService_24162133 {

    private final CartItemDAO_24162133 cartDAO = new CartItemDAOImpl_24162133();
    private final UserDAO_24162133 userDAO = new UserDAOImpl_24162133();
    private final BookService_24162133 bookService = new BookServiceImpl_24162133();

    /** Số lượng tối đa mỗi item trong giỏ */
    private static final int MAX_QUANTITY_PER_ITEM = 10;

    @Override
    public List<CartItem_24162133> getCart(Integer userId) {
        return cartDAO.findByUserId(userId);
    }

    @Override
    public void addToCart(Integer userId, Integer bookId, int quantity) {
        if (quantity <= 0) quantity = 1;

        Book_24162133 book = bookService.findById(bookId);
        if (book == null) throw new RuntimeException("Sách không tồn tại");

        User_24162133 user = userDAO.findById(userId);
        if (user == null) throw new RuntimeException("User không tồn tại");

        CartItem_24162133 existing = cartDAO.findByUserAndBook(userId, bookId);

        if (existing != null) {
            // Cộng dồn
            int newQty = existing.getQuantity() + quantity;
            newQty = clampQuantity(newQty, book);
            existing.setQuantity(newQty);
            cartDAO.update(existing);
        } else {
            int initQty = clampQuantity(quantity, book);
            CartItem_24162133 item = new CartItem_24162133(user, book, initQty);
            cartDAO.insert(item);
        }
    }

    @Override
    public boolean updateQuantity(Integer cartItemId, int newQuantity) {
        CartItem_24162133 item = cartDAO.findById(cartItemId);
        if (item == null) return false;

        if (newQuantity < 1) return false;

        Book_24162133 book = item.getBook();
        int stock = (book.getQuantity() == null) ? 0 : book.getQuantity();

        // Giới hạn: min(MAX_QUANTITY_PER_ITEM, tồn kho)
        int maxAllowed = Math.min(MAX_QUANTITY_PER_ITEM, stock);
        if (maxAllowed < 1) return false;

        if (newQuantity > maxAllowed) {
            newQuantity = maxAllowed;
        }

        item.setQuantity(newQuantity);
        cartDAO.update(item);
        return true;
    }

    @Override
    public void removeItem(Integer cartItemId) {
        cartDAO.delete(cartItemId);
    }

    @Override
    public void clearCart(Integer userId) {
        cartDAO.deleteByUserId(userId);
    }

    @Override
    public long countItems(Integer userId) {
        return cartDAO.countByUserId(userId);
    }

    @Override
    public BigDecimal getTotalAmount(Integer userId) {
        List<CartItem_24162133> items = cartDAO.findByUserId(userId);
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem_24162133 item : items) {
            BigDecimal price = item.getBook().getPrice();
            if (price == null) price = BigDecimal.ZERO;
            total = total.add(price.multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        return total;
    }

    // ===== HÀM PHỤ: giới hạn số lượng =====
    private int clampQuantity(int qty, Book_24162133 book) {
        if (qty < 1) qty = 1;

        int stock = (book.getQuantity() == null) ? 0 : book.getQuantity();
        int maxAllowed = Math.min(MAX_QUANTITY_PER_ITEM, stock);

        if (maxAllowed < 1) {
            throw new RuntimeException("Sách đã hết hàng");
        }
        if (qty > maxAllowed) {
            qty = maxAllowed;
        }
        return qty;
    }
}