package rss.service.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import rss.model.Feed;
import rss.model.channel.Template;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XsoupParserTest {
    public XsoupParser parser = new XsoupParser();

    public String getContent(String fileName) {
        try {
            return new String(Files.readAllBytes(Paths.get(this.getClass().getClassLoader().getResource(fileName).toURI())), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void parseHtmlTest() {
        String content = getContent("tproger.html");
        Document document = Jsoup.parse(content);

        Template template = new Template();
        template.setRoot("article");
        template.setTitle("div[@class=post-title]");
        template.setDescription("div[@class=entry-content]//p");
        template.setLink(".//a");

        Feed feed = parser.getFeed(document, template);

        assertEquals(32, feed.getPosts().size());
        assertEquals("Видеокарта из металлолома и синей изоленты", feed.getPosts().get(0).getTitle());
        assertEquals("Расшифровка видео, в котором автор рассказывает, как работают видеокарты, и собирает свою из максимально простых элементов.", feed.getPosts().get(0).getDescription());
//        assertEquals("Tue, 17 Mar 2020 23:11:43 GMT", feed.getPosts().get(0).getRawPubDate());
//        assertEquals(ZonedDateTime.of(2020, 3, 17, 23, 11, 43, 0, ZoneId.of("GMT")), feed.getPosts().get(0).getPubDate());
//        assertEquals("Kolobok86", feed.getPosts().get(0).getAuthor());
    }

    @Test
    public void parseHtml2Test() {
        String content = getContent("habr.html");
        Document document = Jsoup.parse(content);

        Template template = new Template();
        template.setRoot("article[@class=post]");
        template.setTitle("a[@class=post__title_link]");
        template.setDescription("div[@class=post__text]");
        template.setLink("div[@class=post__body]/a[@class=btn]/@href");
        template.setAuthor("span[@class=user-info__nickname]");

        Feed feed = parser.getFeed(document, template);

        assertEquals(19, feed.getPosts().size());
        assertEquals("Вычисление центра масс за O(1) с помощью интегральных изображений", feed.getPosts().get(1).getTitle());
        assertEquals("В этой статье я расскажу", feed.getPosts().get(1).getDescription());
//        assertEquals("Tue, 17 Mar 2020 23:11:43 GMT", feed.getPosts().get(0).getRawPubDate());
//        assertEquals(ZonedDateTime.of(2020, 3, 17, 23, 11, 43, 0, ZoneId.of("GMT")), feed.getPosts().get(0).getPubDate());
//        assertEquals("Kolobok86", feed.getPosts().get(0).getAuthor());
    }
}
