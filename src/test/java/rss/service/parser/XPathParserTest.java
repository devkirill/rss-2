package rss.service.parser;

import org.junit.Test;
import rss.model.Feed;
import rss.model.channel.Template;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        template.setMain("rss/channel/item");
        template.setHeadline("./title/text()");
        template.setSummary("description");
        template.setPosted("pubDate");
        template.setAuthor("creator");

        Feed feed = parser.parseFeed(stream, template);

        assertEquals(20, feed.getPosts().size());
        assertTrue(feed.getPosts().get(0).getTitle().contains("Chrome 80 stable"));
        assertTrue(feed.getPosts().get(0).getDescription().contains("Начали изучать что это такое"));
        assertEquals("Tue, 17 Mar 2020 23:11:43 GMT", feed.getPosts().get(0).getPubDate());
        assertEquals("Kolobok86", feed.getPosts().get(0).getAuthor());
    }
}