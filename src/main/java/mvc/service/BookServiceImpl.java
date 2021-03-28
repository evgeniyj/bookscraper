package mvc.service;

import mvc.model.Book;
import mvc.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book save(Book book) {
        return repository.save(book);
    }

    @Override
    public Book get(int id) {
        return repository.get(id);
    }

    @Override
    public Book get(String title) {
        return repository.get(title);
    }

    @Override
    public String getNativeIdByTitle(String title) {
        return repository.getNativeIdByTitle(title);
    }

    @Override
    public List<Book> getAll() {
        return repository.getAll();
    }
}
