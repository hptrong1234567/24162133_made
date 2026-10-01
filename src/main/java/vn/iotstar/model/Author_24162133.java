package vn.iotstar.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "author")
public class Author_24162133 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Integer authorId;

    @Column(name = "author_name", length = 100, nullable = false)
    private String authorName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    // ===== QUAN HỆ N-N VỚI BOOK — ĐỂ LAZY ĐỂ TRÁNH VÒNG LẶP =====
    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    private List<Book_24162133> books = new ArrayList<>();

    // ===== CONSTRUCTOR =====
    public Author_24162133() {
    }

    public Author_24162133(String authorName, LocalDate dateOfBirth) {
        this.authorName = authorName;
        this.dateOfBirth = dateOfBirth;
    }

    // ===== GETTER / SETTER =====
    public Integer getAuthorId() { return authorId; }
    public void setAuthorId(Integer authorId) { this.authorId = authorId; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public List<Book_24162133> getBooks() { return books; }
    public void setBooks(List<Book_24162133> books) { this.books = books; }
}