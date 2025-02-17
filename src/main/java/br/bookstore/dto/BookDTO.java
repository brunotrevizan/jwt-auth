package br.bookstore.dto;

import br.bookstore.model.Book;

public class BookDTO {

    private Long id;
    private Long idStore;
    private String cover;
    private String title;
    private String author;
    private String synopsis;
    private int year;
    private int rating;
    private boolean available;

    public BookDTO() {
    }

    public BookDTO(Book book) {
        this.id = book.getId();
        this.idStore = book.getStore().getIdStore();
        this.cover = book.getCover();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.synopsis = book.getSynopsis();
        this.year = book.getYear();
        this.rating = book.getRating();
        this.available = book.isAvailable();
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

    public Long getIdStore() {
        return idStore;
    }

    public void setIdStore(Long idStore) {
        this.idStore = idStore;
    }
}
