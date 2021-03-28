package mvc;

import mvc.model.Book;
import mvc.service.BookService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

import static mvc.utils.Utils.*;


//For manual testing
public class Main {

    public static void main(String[] args) throws IOException {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-mvc-servlet.xml");

        BookService bookService = context.getBean("bookService", BookService.class);

        /*Book book = new Book();

        bookService.save(book);*/

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

        /*SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Book.class)
                .addAnnotatedClass(Chapter.class)
                .buildSessionFactory();

        Session currentSession = factory.getCurrentSession();

        currentSession.beginTransaction();

        currentSession.save(book);

        currentSession.getTransaction().commit();*/

    }
}
