package mvc.service;

import mvc.model.Chapter;
import mvc.repository.ChapterRepository;
import org.springframework.stereotype.Service;

@Service
public class ChapterServiceImpl implements ChapterService {

    private final ChapterRepository repository;

    public ChapterServiceImpl(ChapterRepository repository) {
        this.repository = repository;
    }

    @Override
    public Chapter getByNativeBookIdAndOrderNo(String bookId, int orderNo) {
        return repository.getByNativeBookIdAndOrderNo(bookId, orderNo);
    }
}
