package rss.service.parser;

import org.junit.jupiter.api.Test;
import rss.model.Feed;
import rss.model.channel.Template;

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
    public void parseTest() {
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("habr.xml");
        Template template = new Template();
        template.setRoot("rss/channel/item");
        template.setTitle("title/text()");
        template.setDescription("description");
        template.setPubDate("pubDate");
        template.setAuthor("creator");

        Feed feed = parser.parseFeed(stream, template);

        assertEquals(20, feed.getPosts().size());
        assertTrue(feed.getPosts().get(0).getTitle().contains("Chrome 80 stable"));
        assertTrue(feed.getPosts().get(0).getDescription().contains("Начали изучать что это такое"));
        assertEquals("Tue, 17 Mar 2020 23:11:43 GMT", feed.getPosts().get(0).getRawPubDate());
        assertEquals(ZonedDateTime.of(2020, 3, 17, 23, 11, 43, 0, ZoneId.of("GMT")), feed.getPosts().get(0).getPubDate());
        assertEquals("Kolobok86", feed.getPosts().get(0).getAuthor());
    }

    @Test
    public void parseHtmlTest() {
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("tproger.html");
        Template template = new Template();
        template.setRoot("article");
        template.setTitle("div[@class=post-title]");
        template.setDescription("div[@class=entry-content]//p");
        template.setLink(".//a");

        Feed feed = parser.parseFeed(stream, template);

        assertEquals(32, feed.getPosts().size());
        assertTrue(feed.getPosts().get(0).getTitle().contains("Chrome 80 stable"));
        assertTrue(feed.getPosts().get(0).getDescription().contains("Начали изучать что это такое"));
        assertEquals("Tue, 17 Mar 2020 23:11:43 GMT", feed.getPosts().get(0).getRawPubDate());
        assertEquals(ZonedDateTime.of(2020, 3, 17, 23, 11, 43, 0, ZoneId.of("GMT")), feed.getPosts().get(0).getPubDate());
        assertEquals("Kolobok86", feed.getPosts().get(0).getAuthor());
    }
}