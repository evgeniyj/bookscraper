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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    static String path = "D:\\BaiduYun\\test\\";

    public static void main(String[] args) throws IOException {

        String url = "https://www.blinkist.com/en/nc/daily/";
        String urlAPI = "https://api.blinkist.com/v4/books/";


        Document todayBook = Jsoup.connect(url).get();

        String coverUrl = todayBook.getElementsByClass("book-cover__image")
                .first()
                .attributes()
                .asList()
                .stream()
                .filter(value -> value.getValue().contains("640.jpg"))
                .map(attribute -> attribute.getValue().substring(0, attribute.getValue().length() - 3))
                .collect(Collectors.joining(""));

        //String picUrl = "https://images.blinkist.com/images/books/5d72602c6cee07000897c414/3_4/640.jpg 2x";

        String bookId = coverUrl.split("/")[5];
        //String bookId = "5d7609616cee07000897c50e";
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
        LocalDate publishDate = LocalDate.parse(jsonResult.getString("published_at").substring(0, 10));
        String subtitle = jsonResult.getString("subtitle");
        String author = jsonResult.getString("author");
        String aboutTheBook = jsonResult.getString("about_the_book");
        String readers = jsonResult.getString("who_should_read");
        String aboutTheAuthor = jsonResult.getString("about_the_author");

        List<Chapter> chapters = new ArrayList<>();
        Book book = new Book(bookId, coverUrl, title, subtitle, author, aboutTheAuthor, publishDate, aboutTheBook, readers, chapters);

        if (jsonChapters != null) {
            for (int i = 0; i < jsonChapters.length(); i++) {
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

        String subFolder = title.replace(" ", "_");
        String folder = path + subFolder;

        if (createFolder(folder)) {
            File file = new File(folder + "\\" + subFolder + ".html");
            chapters.forEach(chapter -> {
                try {
                    FileUtils.writeStringToFile(file, chapter.toString(), "UTF8", true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            //chapters.forEach(Chapter::toString);

        }


        for (int i = 0; i < chapters.size(); i++) {
            copyAudioFile(chapters.get(i).getAudioUrl(), i + 1, subFolder);
        }


    }

    private static boolean createFolder(String folderName) {
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

    private static void copyAudioFile(String url, int orderNo, String subFolder) throws IOException {
        //URL realUrl = new URL ("https://hls.blinkist.io/bibs/5d72602c6cee07000897c414/5d7260576cee07000897c415-T1567777138.m4a?Expires=1570094305&Signature=XhwdwQqROEXCvMm~6Bt01tHX18YSMQkou1VmfPZjAdrcVJBpJ~yQSmlyiesSnfBN72MajxQFNU~QjJfJ2nfmSzkMgYpD~6eyu7GN3QCnUh67~988r30j8fup0qN-XLOjgP7xD5YMLMfgcVfhh4g2SIM-FT-G1D-RMvO3F4YnofQ0CoKBDSyt7rqa930bEi7IBqosA7~FzWDap0CbK15x6wxmA32nlNPi2Dd8Ceh0Bx0P18qtiQqYGoOX2zrBqwn9QdvOarLDaQ3mNnSWQH1uVo138GBGacIyTuzAawyqL3y0JsIKwU4tgwsIb5WVUypfcsqkTKtXeK01wO5LcSir5Q__&Key-Pair-Id=APKAJXJM6BB7FFZXUB4A");
        URL realUrl = new URL(url);
        String fileName = orderNo + "_" + FilenameUtils.getName(realUrl.getPath());

        FileUtils.copyURLToFile(
                realUrl,
                new File(path + subFolder + "\\" + fileName),
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
