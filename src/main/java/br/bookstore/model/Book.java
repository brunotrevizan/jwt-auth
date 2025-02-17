package br.bookstore.model;

import br.bookstore.dto.BookDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String cover;

    @Column
    private String title;

    @Column
    private String author;

    @Column
    private String synopsis;

    @Column
    private int year;

    @Column
    private int rating;

    @Column
    private boolean available;

    @ManyToOne(targetEntity = Store.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    public Book() {
    }

    public Book(BookDTO bookDTO) {
        this.id = bookDTO.getId();
        this.cover = bookDTO.getCover();
        this.title = bookDTO.getTitle();
        this.author = bookDTO.getAuthor();
        this.synopsis = bookDTO.getSynopsis();
        this.year = bookDTO.getYear();
        this.rating = bookDTO.getRating();
        this.available = bookDTO.isAvailable();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public void updateFields(BookDTO bookDTO) {
        this.id = bookDTO.getId();
        this.cover = bookDTO.getCover();
        this.title = bookDTO.getTitle();
        this.author = bookDTO.getAuthor();
        this.synopsis = bookDTO.getSynopsis();
        this.year = bookDTO.getYear();
        this.rating = bookDTO.getRating();
        this.available = bookDTO.isAvailable();
    }
}