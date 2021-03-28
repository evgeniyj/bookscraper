package mvc.repository;

import mvc.model.Book;

import java.util.List;

public interface BookRepository {

    Book save(Book book);

    Book get(int id);

    Book get(String title);

    String getNativeIdByTitle(String title);

    List<Book> getAll();

}
