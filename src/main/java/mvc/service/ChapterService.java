package mvc.service;

import mvc.model.Chapter;

public interface ChapterService {

    Chapter getByNativeBookIdAndOrderNo(String bookId, int orderNo);
}
