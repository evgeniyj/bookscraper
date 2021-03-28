package mvc.repository;

import mvc.model.Chapter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ChapterRepositoryImpl implements ChapterRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Chapter getByNativeBookIdAndOrderNo(String bookId, int orderNo) {
        return entityManager.createQuery("from Chapter where nativeBookId = :nativeBookId and orderNo = :orderNo", Chapter.class)
                .setParameter("nativeBookId", bookId)
                .setParameter("orderNo", orderNo)
                .getResultList()
                .get(0);
    }
}
