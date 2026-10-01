package vn.iotstar.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_items")
public class OrderItem_24162133 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Integer orderItemId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = false)
    private Order_24162133 order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", nullable = false)
    private Book_24162133 book;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    // ===== CONSTRUCTOR =====
    public OrderItem_24162133() {
    }

    public OrderItem_24162133(Book_24162133 book, Integer quantity, BigDecimal price) {
        this.book = book;
        this.quantity = quantity;
        this.price = price;
    }

    // ===== GETTER / SETTER =====
    public Integer getOrderItemId() { return orderItemId; }
    public void setOrderItemId(Integer orderItemId) { this.orderItemId = orderItemId; }

    public Order_24162133 getOrder() { return order; }
    public void setOrder(Order_24162133 order) { this.order = order; }

    public Book_24162133 getBook() { return book; }
    public void setBook(Book_24162133 book) { this.book = book; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    // Helper: tính thành tiền
    public BigDecimal getSubtotal() {
        if (price == null || quantity == null) return BigDecimal.ZERO;
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}