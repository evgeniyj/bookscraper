package mvc.web;

import mvc.model.Chapter;
import mvc.service.BookService;
import mvc.service.ChapterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ChapterController {

    private final ChapterService chapterService;
    private final BookService bookService;


    public ChapterController(ChapterService chapterService, BookService bookService) {
        this.chapterService = chapterService;
        this.bookService = bookService;
    }

    @RequestMapping("/books/read/{title}/{id}")
    public ModelAndView getChapter(@PathVariable("title") String title, @PathVariable("id") int orderNo) {

        ModelAndView mav = new ModelAndView("chapter");

        title = title.replaceAll("_", " ");

        String nativeBookId = bookService.getNativeIdByTitle(title);

        Chapter chapter = chapterService.getByNativeBookIdAndOrderNo(nativeBookId, orderNo - 1);

        mav.addObject("chapter", chapter);
        mav.addObject("title", title);

        return mav;


    }
}
