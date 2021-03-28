package mvc.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "book")
public class Book implements Serializable {

    @Id
    @Column(name = "id")
    private int id;

    @NaturalId
    @Column(name = "book_id")
    private String nativeId;

    @Column(name = "cover")
    private String cover;

    @Column(name = "title")
    private String title;

    @Column(name = "subtitle")
    private String subtitle;

    @Column(name = "author")
    private String author;

    @Column(name = "about_the_author")
    private String aboutTheAuthor;

    @Column(name = "publish_date")
    private LocalDate publishDate;

    @Column(name = "about_the_book")
    private String aboutTheBook;

    @Column(name = "readers")
    private String readers;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Chapter> chapters;

    public Book() {
    }

    public Book(String nativeId, String cover, String title, String subtitle, String author, String aboutTheAuthor, LocalDate publishDate, String aboutTheBook, String readers, List<Chapter> chapters) {
        this.nativeId = nativeId;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setNativeId(String nativeId) {
        this.nativeId = nativeId;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setAboutTheAuthor(String aboutTheAuthor) {
        this.aboutTheAuthor = aboutTheAuthor;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public void setAboutTheBook(String aboutTheBook) {
        this.aboutTheBook = aboutTheBook;
    }

    public void setReaders(String readers) {
        this.readers = readers;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public int getId() {
        return id;
    }

    public String getNativeId() {
        return nativeId;
    }

    public String getCover() {
        return cover;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getAuthor() {
        return author;
    }

    public String getAboutTheAuthor() {
        return aboutTheAuthor;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public String getAboutTheBook() {
        return aboutTheBook;
    }

    public String getReaders() {
        return readers;
    }
}
