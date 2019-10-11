package model;

public class Chapter {

    private final String id;

    private final int orderNo;

    private final String title;

    private final String text;

    private final String audioUrl;

    public Chapter(String id, int orderNo, String title, String text, String audioUrl) {
        this.id = id;
        this.orderNo = orderNo;
        this.title = title;
        this.text = text;
        this.audioUrl = audioUrl;
    }

    public String getId() {
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

    @Override
    public String toString() {
        return title + '\n' + text;
    }
}
