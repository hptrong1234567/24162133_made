package vn.iotstar.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "rating")
@IdClass(RatingId_24162133.class)
public class Rating_24162133 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userid", nullable = false)
    private User_24162133 user;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bookid", nullable = false)
    private Book_24162133 book;

    @Column(name = "rating")
    private Short rating;

    @Column(name = "review_text", columnDefinition = "text")
    private String reviewText;

    // ===== CONSTRUCTOR =====
    public Rating_24162133() {
    }

    public Rating_24162133(User_24162133 user, Book_24162133 book, Short rating, String reviewText) {
        this.user = user;
        this.book = book;
        this.rating = rating;
        this.reviewText = reviewText;
    }

    // ===== GETTER / SETTER =====
    public User_24162133 getUser() { return user; }
    public void setUser(User_24162133 user) { this.user = user; }

    public Book_24162133 getBook() { return book; }
    public void setBook(Book_24162133 book) { this.book = book; }

    public Short getRating() { return rating; }
    public void setRating(Short rating) { this.rating = rating; }

    public String getReviewText() { return reviewText; }
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rating_24162133)) return false;
        Rating_24162133 that = (Rating_24162133) o;
        return Objects.equals(user, that.user) && Objects.equals(book, that.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, book);
    }
}