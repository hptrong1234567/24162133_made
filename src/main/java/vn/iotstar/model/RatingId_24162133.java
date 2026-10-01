package vn.iotstar.model;

import java.io.Serializable;
import java.util.Objects;

public class RatingId_24162133 implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer user;
    private Integer book;

    public RatingId_24162133() {
    }

    public RatingId_24162133(Integer user, Integer book) {
        this.user = user;
        this.book = book;
    }

    public Integer getUser() { return user; }
    public void setUser(Integer user) { this.user = user; }

    public Integer getBook() { return book; }
    public void setBook(Integer book) { this.book = book; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RatingId_24162133)) return false;
        RatingId_24162133 that = (RatingId_24162133) o;
        return Objects.equals(user, that.user) && Objects.equals(book, that.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, book);
    }
}