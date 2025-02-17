package br.bookstore.service;

import br.bookstore.dto.BookDTO;
import br.bookstore.model.Book;
import br.bookstore.model.BookStoreException;
import br.bookstore.model.Store;
import br.bookstore.utils.JsonUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.exception.DataException;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class BookService {

    EntityManager entityManager;

    @Inject
    public BookService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public BookDTO createBook(BookDTO bookDTO, Long idStore) throws BookStoreException {
        try {
            Store store = entityManager.find(Store.class, idStore);
            Book book = new Book(bookDTO);
            book.setStore(store);
            entityManager.persist(book);
            entityManager.flush();
            return new BookDTO(book);
        } catch (DataException e) {
            throw new BookStoreException("A imagem enviada em base64 é muito grande, tente com uma imagem menor.");
        }
    }

    @Transactional
    public void editBook(BookDTO bookDTO) throws BookStoreException {
        Book book = entityManager.find(Book.class, bookDTO.getId());
        if (book != null) {
            book.updateFields(bookDTO);
            entityManager.merge(book);
            entityManager.flush();
        } else {
            throw new BookStoreException("Livro não existe.");
        }
    }

    public BookDTO getBook(Long idBook) {
        Book book = entityManager.find(Book.class, idBook);
        return book != null ? new BookDTO(book) : null;
    }

    @Transactional
    public void deleteBook(Long idBook) throws BookStoreException {
        Book book = entityManager.find(Book.class, idBook);
        if (book != null) {
            entityManager.remove(book);
            entityManager.flush();
        } else {
            throw new BookStoreException("Livro não existe.");
        }
    }

    public List<BookDTO> searchBooks(int limit, int offset, String author, String title, int yearStart, int yearFinish, int rating, Boolean available, Long idStore) {
        var query = entityManager.createNativeQuery(createQuerySearchBooks(limit, offset, author, title, yearStart, yearFinish, rating, available, idStore));
        var resultados = (String) query.getSingleResult();
        return resultados != null ? JsonUtils.toList(resultados, BookDTO.class) : new ArrayList<>();
    }

    private String createQuerySearchBooks(int limit, int offset, String author, String title, int yearStart, int yearFinish, int rating, Boolean available, Long idStore) {
        StringBuilder query = new StringBuilder();
        query.append("WITH books as ( " +
                "    SELECT * from" +
                "    book b" +
                "    WHERE b.store_id = " + idStore + " AND ");
        if (author != null)
            query.append("    author ILIKE '%" + author + "%' AND ");
        if (title != null)
            query.append("    title ILIKE '%" + title + "%' AND ");
        if (yearStart > 0 && yearFinish > 0)
            query.append("    year BETWEEN " + yearStart + " AND " + yearFinish + " AND ");
        if (rating > 0)
            query.append("    rating = " + rating + " AND ");
        if (available != null)
            query.append("    available = " + available + " AND ");
        query.append(" 1 = 1 ");
        query.append(" LIMIT " + limit + " OFFSET " + offset + ")" +
                " SELECT" +
                "    json_agg(json_build_object(" +
                "   'id', id, " +
                "   'idStore', store_id, " +
                "   'cover', cover, " +
                "   'title', title, " +
                "   'author', author," +
                "   'synopsis', synopsis, " +
                "   'year', year, " +
                "   'rating', rating, " +
                "   'available', available))" +
                " FROM books");


        return query.toString();
    }
}
