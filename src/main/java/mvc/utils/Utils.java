package mvc.utils;

import mvc.model.Book;
import mvc.model.Chapter;
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
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static mvc.model.Constants.*;

public class Utils {

    public static String copyFileToLocalStorage(String url, int orderNo, String subFolder) throws IOException {

        URL realUrl = new URL(url);

        String fileName = orderNo > 9 ?
                orderNo + "_" + FilenameUtils.getName(realUrl.getPath()) :
                "0" + orderNo + "_" + FilenameUtils.getName(realUrl.getPath());

        File coverFile = new File(path + subFolder + File.separator + fileName);

        while (true) {
            try {
                URLConnection conn = realUrl.openConnection();
                conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:31.0) Gecko/20100101 Firefox/31.0");
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(100000);
                conn.connect();
                FileUtils.copyInputStreamToFile(conn.getInputStream(), coverFile);
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return fileName;
    }

    public static String getAudioLink(String audioUrl) throws IOException {

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

    public static boolean createFolder(String folderName) {
        boolean result = true;
        Path folder = Paths.get(folderName);
        //if directory exists?
        if (!Files.exists(folder)) {
            try {
                Files.createDirectories(folder);
                result = true;
            } catch (IOException e) {
                //fail to create directory
                e.printStackTrace();
                result = false;
            }
        }
        return result;
    }

    public static void getAudioAndCover(Book book) throws IOException {
        String subFolder = book.getTitle().replace(" ", "_");
        String folder = path + subFolder;

        if (createFolder(folder)) {
            File file = new File(folder + File.separator + subFolder + ".html");
            book.getChapters().forEach(chapter -> {
                try {
                    FileUtils.writeStringToFile(file, chapter.toString(), "UTF8", true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        for (int i = 0; i < book.getChapters().size(); i++) {
            String fileName = copyFileToLocalStorage(book.getChapters().get(i).getAudioUrl(), i + 1, subFolder);
            book.getChapters().get(i).setAudioUrl(fileName);
        }

        String coverPath = copyFileToLocalStorage(book.getCover(), 0, subFolder);
        book.setCover(coverPath);
    }

    public static void populateChaptersFromJSON(Book book, JSONArray jsonChapters) throws IOException {
        if (jsonChapters != null) {
            for (int i = 0; i < jsonChapters.length(); i++) {
                JSONObject jsonObject = jsonChapters.getJSONObject(i);
                String chapterId = jsonObject.getString("id");
                int orderNo = jsonObject.getInt("order_no");
                String chapterTitle = jsonObject.getString("title");
                String text = jsonObject.getString("text");
                String audioUrl = "https://www.blinkist.com/api/books/"
                        + book.getNativeId()
                        + "/chapters/"
                        + chapterId
                        + "/audio";

                audioUrl = getAudioLink(audioUrl);

                Chapter tmpChapter = new Chapter(chapterId, book.getNativeId(), orderNo, chapterTitle, text, audioUrl);
                tmpChapter.setBook(book);
                book.getChapters().add(tmpChapter);
            }
        }
    }

    public static String getContent() {
        String content = null;
        URLConnection connection = null;
        try {
            connection = new URL(url).openConnection();
            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\Z");
            content = scanner.next();
            scanner.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        content = content.replaceAll("\\\\\"", "\"");
        return content;
    }

    public static String getBookId(Document todayBook) {
        String coverUrl = todayBook.getElementsByClass("book-cover__image")
                .first()
                .attributes()
                .asList()
                .stream()
                .filter(value -> value.getValue().contains("640.jpg"))
                .map(attribute -> attribute.getValue().substring(0, attribute.getValue().length() - 3))
                .collect(Collectors.joining(""));

        return coverUrl.split("/")[5];
    }

    public static JSONObject getTodayBookInJSON(String bookId) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(urlAPI + bookId);
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String json = "";
        try {
            response = client.execute(httpGet);
            entity = response.getEntity();
            json = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new JSONObject(json).getJSONObject("book");
    }

    public static Book getBookFromJSON(JSONObject todayBookInJSON, String bookId) {
        String coverUrl = "https://images.blinkist.com/images/books/" + bookId + "/1_1/640.jpg";
        String title = todayBookInJSON.getString("title");
        LocalDate publishDate = LocalDate.parse(todayBookInJSON.getString("published_at").substring(0, 10));
        String subtitle = todayBookInJSON.getString("subtitle");
        String author = todayBookInJSON.getString("author");
        String aboutTheBook = todayBookInJSON.getString("about_the_book");
        String readers = todayBookInJSON.getString("who_should_read");
        String aboutTheAuthor = todayBookInJSON.getString("about_the_author");
        List<Chapter> chapters = new ArrayList<>();
        return new Book(bookId, coverUrl, title, subtitle, author, aboutTheAuthor, publishDate, aboutTheBook, readers, chapters);
    }
}
