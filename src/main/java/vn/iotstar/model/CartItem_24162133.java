package vn.iotstar.model;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@Table(name = "cart_items")
public class CartItem_24162133 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Integer cartItemId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User_24162133 user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", nullable = false)
    private Book_24162133 book;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // ===== CONSTRUCTOR =====
    public CartItem_24162133() {
    }

    public CartItem_24162133(User_24162133 user, Book_24162133 book, Integer quantity) {
        this.user = user;
        this.book = book;
        this.quantity = quantity;
        this.createdAt = LocalDateTime.now();
    }

    // ===== GETTER / SETTER =====
    public Integer getCartItemId() { return cartItemId; }
    public void setCartItemId(Integer cartItemId) { this.cartItemId = cartItemId; }

    public User_24162133 getUser() { return user; }
    public void setUser(User_24162133 user) { this.user = user; }

    public Book_24162133 getBook() { return book; }
    public void setBook(Book_24162133 book) { this.book = book; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}