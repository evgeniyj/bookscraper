package mvc.repository;

import mvc.model.Chapter;

public interface ChapterRepository {

    Chapter getByNativeBookIdAndOrderNo(String bookId, int orderNo);

}
