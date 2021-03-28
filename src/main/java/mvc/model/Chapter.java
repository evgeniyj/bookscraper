package mvc.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "chapter")
public class Chapter {

    @ManyToOne
    @JoinColumn(name = "native_book_id", referencedColumnName = "book_id")
    private Book book;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id")
    private int id;

    @Column(name = "native_chapter_id")
    private String nativeId;

    @Column(name = "native_book_id", insertable = false, updatable = false)
    private String nativeBookId;

    @Column(name = "order_no")
    private int orderNo;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @Column(name = "audioUrl")
    private String audioUrl;

    public Chapter() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public Chapter(String nativeId, String nativeBookId, int orderNo, String title, String text, String audioUrl) {
        this.nativeId = nativeId;
        this.nativeBookId = nativeBookId;
        this.orderNo = orderNo;
        this.title = title;
        this.text = text;
        this.audioUrl = audioUrl;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getNativeId() {
        return nativeId;
    }

    public int getId() {
        return id;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public String getNativeBookId() {
        return nativeBookId;
    }

    public void setNativeId(String nativeId) {
        this.nativeId = nativeId;
    }

    public void setNativeBookId(String nativeBookId) {
        this.nativeBookId = nativeBookId;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    @Override
    public String toString() {
        return title + '\n' + text;
    }
}
