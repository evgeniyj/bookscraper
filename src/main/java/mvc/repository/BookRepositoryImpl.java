package mvc.repository;

import mvc.model.Book;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class BookRepositoryImpl implements BookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Book save(Book book) {
        entityManager.persist(book);
        return book;
    }

    @Override
    public Book get(int id) {
        return entityManager.find(Book.class, id);
    }

    @Override
    public Book get(String title) {
        return entityManager.createQuery("from Book where title = :title ", Book.class)
                .setParameter("title", title)
                .getResultList()
                .get(0);
    }

    @Override
    public String getNativeIdByTitle(String title) {
        return entityManager.createQuery("select b.nativeId from Book b where title = :title ", String.class)
                .setParameter("title", title)
                .getResultList()
                .get(0);
    }

    @Override
    public boolean isInStorage(String nativeId) {
        List<Book> resultList = entityManager.createQuery("from Book where nativeId = :nativeId", Book.class)
                .setParameter("nativeId", nativeId)
                .getResultList();

        return resultList.size() > 0;
    }

    @Override
    public List<Book> getAll() {
        return entityManager.createQuery("from Book").getResultList();
    }
}
