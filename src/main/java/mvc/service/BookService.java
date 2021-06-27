package mvc.service;

import mvc.model.Book;

import java.util.List;

public interface BookService {

    Book save(Book book);

    Book get(int id);

    Book get(String title);

    String getNativeIdByTitle(String title);

    boolean isInStorage(String nativeId);

    List<Book> getAll();

}
