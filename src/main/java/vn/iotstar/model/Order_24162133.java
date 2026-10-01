package vn.iotstar.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order_24162133 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User_24162133 user;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "total_amount", precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "payment_method", length = 50)
    private String paymentMethod;   // "COD"

    @Column(name = "status", length = 50)
    private String status;          // "PENDING", "CONFIRMED", "SHIPPING", "DELIVERED", "CANCELLED"

    @Column(name = "shipping_address", length = 500)
    private String shippingAddress;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "note", length = 500)
    private String note;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<OrderItem_24162133> items = new ArrayList<>();

    // ===== CONSTRUCTOR =====
    public Order_24162133() {
    }

    // ===== GETTER / SETTER =====
    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }

    public User_24162133 getUser() { return user; }
    public void setUser(User_24162133 user) { this.user = user; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public List<OrderItem_24162133> getItems() { return items; }
    public void setItems(List<OrderItem_24162133> items) { this.items = items; }

    // Helper
    public void addItem(OrderItem_24162133 item) {
        items.add(item);
        item.setOrder(this);
    }
}