package rss.service.loader;

import org.junit.jupiter.api.Test;
import rss.model.db.Feed;
import rss.model.db.Post;
import rss.service.loader.parser.XPathParser;
import rss.utils.DateUtil;
import rss.utils.DefaultTemplate;

import java.io.InputStream;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class XPathParserTest {
    public XPathParser parser;

    public XPathParserTest() {
        try {
            this.parser = new XPathParser();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void parseXmlTest() {
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("habr.xml");

        Feed feed = parser.parseFeed(stream, DefaultTemplate.getRssTemplate());

        assertEquals("Все публикации подряд", feed.getTitle());
        assertEquals("Все публикации подряд на Хабре", feed.getDescription());
        assertEquals("ru", feed.getLanguage());
        assertEquals("https://habr.com/images/logo.png", feed.getImage());
        assertEquals("https://habr.com/ru/all/all/", feed.getLink());
        assertEquals(DateUtil.parse("Tue, 17 Mar 2020 23:14:47 GMT").toEpochSecond(), feed.getPubDate().toEpochSecond());
        assertEquals(20, feed.getPosts().size());

        Post post = feed.getPosts().get(0);

        assertTrue(post.getTitle().contains("Chrome 80 stable"));
        assertTrue(post.getDescription().contains("Начали изучать что это такое"));
        assertEquals("https://habr.com/ru/post/492830/", post.getGuid());
        assertEquals("https://habr.com/ru/post/492830/?utm_source=habrahabr&utm_medium=rss&utm_campaign=492830", post.getLink());
        assertEquals("Tue, 17 Mar 2020 23:11:43 GMT", post.getRawPubDate());
        assertEquals(ZonedDateTime.of(2020, 3, 17, 23, 11, 43, 0, ZoneId.of("GMT")).toEpochSecond(), post.getPubDate().toEpochSecond());
        assertEquals("Kolobok86", post.getAuthor());
    }

    @Test
    public void parseXml2Test() {
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("tproger.xml");

        Feed feed = parser.parseFeed(stream, DefaultTemplate.getRssTemplate());

        assertEquals("Tproger", feed.getTitle());
        assertEquals("Актуальные новости из мира IT, регулярные образовательные статьи и переводы, поток информации для программистов и всех, кто связан с миром разработки.", feed.getDescription());
        assertEquals("ru-RU", feed.getLanguage());
        assertEquals("https://tproger.ru/apple-touch-icon.png", feed.getImage());
        assertEquals("https://tproger.ru", feed.getLink());
        assertEquals(DateUtil.parse("Fri, 27 Mar 2020 20:17:05 +0000").toEpochSecond(), feed.getPubDate().toEpochSecond());
        assertEquals(50, feed.getPosts().size());

        Post post = feed.getPosts().get(0);

        assertTrue(post.getTitle().contains("Домашний разраб"));
        assertTrue(post.getDescription().contains("hackathon"));
        assertEquals("https://tproger.ru/?p=127043", post.getGuid());
        assertEquals("https://tproger.ru/events/devdom-hackathon/", post.getLink());
        assertEquals("Fri, 27 Mar 2020 17:12:46 +0000", post.getRawPubDate());
        assertEquals(ZonedDateTime.of(2020, 3, 27, 17, 12, 46, 0, ZoneId.of("GMT")).toEpochSecond(), post.getPubDate().toEpochSecond());
        assertEquals("Тимур Кондратьев", post.getAuthor());
    }
}