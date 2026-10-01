package vn.iotstar.model;

import java.io.Serializable;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User_24162133 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "fullname", length = 100, nullable = false)
    private String fullname;

    @Column(name = "phone")
    private Integer phone;

    @Column(name = "passwd", length = 100, nullable = false)
    private String passwd;

    @Column(name = "signup_date")
    private LocalDateTime signupDate;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "is_admin")
    private Boolean isAdmin = false;

    // ===== QUAN HỆ 1 USER - N CART_ITEM =====
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<CartItem_24162133> cartItems = new ArrayList<>();

    // ===== QUAN HỆ 1 USER - N ORDER =====
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Order_24162133> orders = new ArrayList<>();

    // ===== CONSTRUCTOR =====
    public User_24162133() {
    }

    public User_24162133(String email, String fullname, String passwd) {
        this.email = email;
        this.fullname = fullname;
        this.passwd = passwd;
        this.signupDate = LocalDateTime.now();
        this.isAdmin = false;
    }

    // ===== GETTER / SETTER =====
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public Integer getPhone() { return phone; }
    public void setPhone(Integer phone) { this.phone = phone; }

    public String getPasswd() { return passwd; }
    public void setPasswd(String passwd) { this.passwd = passwd; }

    public LocalDateTime getSignupDate() { return signupDate; }
    public void setSignupDate(LocalDateTime signupDate) { this.signupDate = signupDate; }

    public LocalDateTime getLastLogin() { return lastLogin; }
    public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }

    public Boolean getIsAdmin() { return isAdmin; }
    public void setIsAdmin(Boolean isAdmin) { this.isAdmin = isAdmin; }

    public List<CartItem_24162133> getCartItems() { return cartItems; }
    public void setCartItems(List<CartItem_24162133> cartItems) { this.cartItems = cartItems; }

    public List<Order_24162133> getOrders() { return orders; }
    public void setOrders(List<Order_24162133> orders) { this.orders = orders; }
}