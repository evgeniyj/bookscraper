package mvc.web;


import mvc.model.Book;
import mvc.service.BookService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static mvc.utils.Utils.*;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Scheduled(cron = "0 10 9 * * ?")
    public void getTodayBook() throws IOException {
        String content = getContent();

        Document todayBook = Jsoup.parse(content);

        String bookId = getBookId(todayBook);

        JSONObject todayBookInJSON = getTodayBookInJSON(bookId);

        Book book = getBookFromJSON(todayBookInJSON, bookId);

        JSONArray jsonChapters = todayBookInJSON.getJSONArray("chapters");

        System.out.println(todayBookInJSON);

        populateChaptersFromJSON(book, jsonChapters);

        getAudioAndCover(book);

        bookService.save(book);
    }

    @RequestMapping("/")
    public ModelAndView showBooks() {

        List<Book> bookList = bookService.getAll();
        Collections.reverse(bookList);

        ModelAndView mav = new ModelAndView("books");
        mav.addObject("bookList", bookList);
        return mav;
    }

    @RequestMapping("/books/{title}")
    public ModelAndView getBook(@PathVariable("title") String title) {

        ModelAndView mav = new ModelAndView("book");

        title = title.replaceAll("_", " ");

        Book book = bookService.get(title);


        mav.addObject("book", book);

        return mav;

    }
}
