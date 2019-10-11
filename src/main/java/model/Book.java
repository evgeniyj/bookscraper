package model;

import java.time.LocalDate;
import java.util.List;

public class Book {

    private final String id;

    private final String cover;

    private final String title;

    private final String subtitle;

    private final String author;

    private final String aboutTheAuthor;

    private final LocalDate publishDate;

    private final String aboutTheBook;

    private final String readers;

    private final List<Chapter> chapters;

    public Book(String id, String cover, String title, String subtitle, String author, String aboutTheAuthor, LocalDate publishDate, String aboutTheBook, String readers, List<Chapter> chapters) {
        this.id = id;
        this.cover = cover;
        this.title = title;
        this.subtitle = subtitle;
        this.author = author;
        this.aboutTheAuthor = aboutTheAuthor;
        this.publishDate = publishDate;
        this.aboutTheBook = aboutTheBook;
        this.readers = readers;
        this.chapters = chapters;
    }

}
