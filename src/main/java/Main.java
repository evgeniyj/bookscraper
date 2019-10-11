import model.Book;
import model.Chapter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {

        /*URL realUrl = new URL ("https://hls.blinkist.io/bibs/5d72602c6cee07000897c414/5d7260576cee07000897c415-T1567777138.m4a?Expires=1570094305&Signature=XhwdwQqROEXCvMm~6Bt01tHX18YSMQkou1VmfPZjAdrcVJBpJ~yQSmlyiesSnfBN72MajxQFNU~QjJfJ2nfmSzkMgYpD~6eyu7GN3QCnUh67~988r30j8fup0qN-XLOjgP7xD5YMLMfgcVfhh4g2SIM-FT-G1D-RMvO3F4YnofQ0CoKBDSyt7rqa930bEi7IBqosA7~FzWDap0CbK15x6wxmA32nlNPi2Dd8Ceh0Bx0P18qtiQqYGoOX2zrBqwn9QdvOarLDaQ3mNnSWQH1uVo138GBGacIyTuzAawyqL3y0JsIKwU4tgwsIb5WVUypfcsqkTKtXeK01wO5LcSir5Q__&Key-Pair-Id=APKAJXJM6BB7FFZXUB4A");
        String fileName = "1_" + FilenameUtils.getName(realUrl.getPath());

        FileUtils.copyURLToFile(
                realUrl,
                new File("C:\\test\\"+fileName),
                1000, 1000);*/

        String url = "https://www.blinkist.com/en/nc/daily/";
        String urlAPI = "https://api.blinkist.com/v4/books/";

        //https://www.blinkist.com/en/nc/daily/reader/a-walk-in-the-woods-en



        //Document todayBook = Jsoup.parse(Constants.html2);
        Document todayBook = Jsoup.connect(url).get();

        String coverUrl = todayBook.getElementsByClass("daily-book__image")
                .first()
                .attributes()
                .asList()
                .stream()
                .filter(value -> value.getValue().contains("640.jpg"))
                .map(attribute -> attribute.getValue().substring(0, attribute.getValue().length()-3))
                .collect(Collectors.joining(""));

        //String picUrl = "https://images.blinkist.com/images/books/5d72602c6cee07000897c414/3_4/640.jpg 2x";

        String bookId = coverUrl.split("/")[5];
        //String bookId = "5d6d558a6cee0700081a69d7";
        String chapterId = "";

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(urlAPI + bookId);


        CloseableHttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String json = EntityUtils.toString(entity, StandardCharsets.UTF_8);

        JSONObject jsonResult = new JSONObject(json).getJSONObject("book");
        //JSONObject jsonObject = jsonResult.getJSONObject("book");

        JSONArray jsonChapters = jsonResult.getJSONArray("chapters");
        System.out.println(json);
        //assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();

        //String coverUrl = "https://images.blinkist.com/images/books/5d6d558a6cee0700081a69d7/3_4/640.jpg";

        String title = jsonResult.getString("title");
        LocalDate publishDate = LocalDate.parse(jsonResult.getString("published_at").substring(0,10));
        String subtitle = jsonResult.getString("subtitle");
        String author = jsonResult.getString("author");
        String aboutTheBook = jsonResult.getString("about_the_book");
        String readers = jsonResult.getString("who_should_read");
        String aboutTheAuthor = jsonResult.getString("about_the_author");

        List<Chapter> chapters = new ArrayList<>();
        Book book = new Book(bookId, coverUrl, title, subtitle, author, aboutTheAuthor, publishDate, aboutTheBook, readers, chapters);

        if (jsonChapters != null) {
            for (int i = 0 ; i < jsonChapters.length() ; i++) {
                JSONObject jsonObject = jsonChapters.getJSONObject(i);
                chapterId = jsonObject.getString("id");
                int orderNo = jsonObject.getInt("order_no");
                String chapterTitle = jsonObject.getString("title");
                String text = jsonObject.getString("text");
                String audioUrl = "https://www.blinkist.com/api/books/"
                        + bookId
                        + "/chapters/"
                        + chapterId
                        + "/audio";

                audioUrl = getAudioLink(audioUrl);


                chapters.add(new Chapter(chapterId, orderNo, chapterTitle, text, audioUrl));
            }
        }

        chapters.forEach(System.out::println);

        for (int i = 0; i < chapters.size(); i++) {
            getAudioFile(chapters.get(i).getAudioUrl(), i+1);
        }

        //getAudioFile();

        /*for (int i = 1; i <= chapters.size(); i++) {

            URL realUrl = new URL (chapters.get(i).getAudioUrl());
            String fileName = i + "_" + FilenameUtils.getName(realUrl.getPath());

            FileUtils.copyURLToFile(
                    realUrl,
                    new File("C:\\test\\"+fileName),
                    1000, 1000);
        }*/




        //Document blink = Jsoup.connect(link).get();

        Document document3 = Jsoup.parse(Constants.book);

        //String bookId = document3.getElementsByAttribute("data-book-id").attr("data-book-id");
        System.out.println(bookId);


        //Elements chapters = document3.getElementsByClass("chapter chapter");

        List<String> bookByChapters = new ArrayList<>();

/*        for (Element element:chapters) {
            chapterId = element.attr("data-chapterid");
            System.out.println(chapterId);
        }*/



        //String url = "https://www.blinkist.com/en/nc/daily/";
        //String url = "https://www.blinkist.com/en/nc/daily/reader/happy-en";

        /*Connection connect = HttpConnection.connect(url);
        Connection.Response execute = connect.execute();
        Connection.Response response = connect.response();
        String body = response.body();*/

        //Document document = Jsoup.connect(url).get();
        Document document = Jsoup.parse(Constants.html);

        Node title1 = document.getElementsByClass("daily-book__headline")
                .first()
                .childNodes()
                .get(0);

        Node author1 = document
                .getElementsByClass("daily-book__author")
                .first()
                .childNodes()
                .get(0);

        String link = document.getElementsByClass("cta cta--play daily-book__cta")
                .first()
                .attributes()
                .asList()
                .get(1)
                .getValue();

        link = url + link.substring(13);



        Document document2 = null;

        String synopsis = document2.getElementsByClass("book-tabs__content-inner")
                .first()
                .childNodes()
                .stream()
                .filter(s -> s.toString().length() > 0)
                .map(Node::toString)
                .collect(Collectors.joining(""));

        String audience = document2.getElementsByClass("book-tabs__content-inner")
                .get(1)
                .childNodes()
                .stream()
                .filter(s -> s.toString().length() > 0)
                .map(Node::toString)
                .collect(Collectors.joining(""));

        String authorDetails = document2.getElementsByClass("book-tabs__content-inner")
                .get(2)
                .childNodes()
                .stream()
                .filter(s -> s.toString().length() > 0)
                .map(Node::toString)
                .collect(Collectors.joining(""));





/*        for (Element element:chapters) {
            String content = element.getElementsByClass("chapter__content")
                    .first()
                    .childNodes()
                    .stream()
                    .map(Node::toString)
                    .collect(Collectors.joining(""));
            bookByChapters.add(content);
        }*/



        //chapter__content


        System.out.println(synopsis);
        System.out.println(audience);
        System.out.println(authorDetails);
        System.out.println(coverUrl);


    }

    private static void getAudioFile(String url, int orderNo) throws IOException {
        //URL realUrl = new URL ("https://hls.blinkist.io/bibs/5d72602c6cee07000897c414/5d7260576cee07000897c415-T1567777138.m4a?Expires=1570094305&Signature=XhwdwQqROEXCvMm~6Bt01tHX18YSMQkou1VmfPZjAdrcVJBpJ~yQSmlyiesSnfBN72MajxQFNU~QjJfJ2nfmSzkMgYpD~6eyu7GN3QCnUh67~988r30j8fup0qN-XLOjgP7xD5YMLMfgcVfhh4g2SIM-FT-G1D-RMvO3F4YnofQ0CoKBDSyt7rqa930bEi7IBqosA7~FzWDap0CbK15x6wxmA32nlNPi2Dd8Ceh0Bx0P18qtiQqYGoOX2zrBqwn9QdvOarLDaQ3mNnSWQH1uVo138GBGacIyTuzAawyqL3y0JsIKwU4tgwsIb5WVUypfcsqkTKtXeK01wO5LcSir5Q__&Key-Pair-Id=APKAJXJM6BB7FFZXUB4A");
        URL realUrl = new URL (url);
        String fileName =  orderNo + "_" + FilenameUtils.getName(realUrl.getPath());

        FileUtils.copyURLToFile(
                realUrl,
                new File("C:\\test\\"+fileName),
                10000, 100000);
    }

    private static String getAudioLink(String audioUrl) throws IOException {

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(audioUrl);
        httpGet.setHeader("X-Requested-With", "XMLHttpRequest");


        CloseableHttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String json = EntityUtils.toString(entity, StandardCharsets.UTF_8);

        audioUrl = new JSONObject(json).getString("url");

        client.close();
        return audioUrl;
    }

}
