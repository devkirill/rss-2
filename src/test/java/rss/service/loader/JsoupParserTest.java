package rss.service.loader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import rss.model.db.Feed;
import rss.model.db.Post;
import rss.model.db.Template;
import rss.service.loader.parser.JsoupParser;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsoupParserTest {
    public JsoupParser parser = new JsoupParser();

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
        template.getPost().setTitle("div.post-title");
        template.getPost().setDescription("div.entry-content p");
        template.getPost().setLink("a");

        Feed feed = parser.parse(document, template);

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
        template.setRoot("article.post");
        template.getPost().setTitle("a.post__title_link");
        template.getPost().setDescription("div.post__text @");
        template.getPost().setGuid("div.post__body a.btn @href");
        template.getPost().setLink("div.post__body a.btn @href");
        template.getPost().setAuthor("span.user-info__nickname");

        Feed feed = parser.parse(document, template);

        assertEquals(19, feed.getPosts().size());
        Post post = feed.getPosts().get(1);
        assertEquals("Вычисление центра масс за O(1) с помощью интегральных изображений", post.getTitle());
        assertTrue(post.getDescription().contains("img"));
        assertTrue(post.getDescription().contains("https://habrastorage.org/webt/jz/zu/un/jzzuunk1lhxykcm7wzw8abqoaek.png"));
        assertTrue(post.getDescription().contains("В этой статье я расскажу"));
        assertTrue(post.getDescription().contains("Какой недостаток имеет это решение и как его исправить"));
        assertEquals("bad3p", post.getAuthor());
        assertEquals("https://habr.com/ru/company/pixonic/blog/493300/#habracut", post.getLink());
//        assertEquals("Tue, 17 Mar 2020 23:11:43 GMT", feed.getPosts().get(0).getRawPubDate());
//        assertEquals(ZonedDateTime.of(2020, 3, 17, 23, 11, 43, 0, ZoneId.of("GMT")), feed.getPosts().get(0).getPubDate());
//        assertEquals("Kolobok86", feed.getPosts().get(0).getAuthor());
    }
}
