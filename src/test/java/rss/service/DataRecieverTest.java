package rss.service;

import org.junit.Test;
import rss.utils.StreamUtil;

import java.io.InputStream;

import static org.junit.Assert.assertTrue;

public class DataRecieverTest {
    private DataReciever dataReciever = new DataReciever();

    @Test
    public void getContent() {
        InputStream stream = dataReciever.getContent("https://habr.com/ru/rss/all/all/");

        String content = StreamUtil.readStream(stream);

        assertTrue(content.length() > 128);
        assertTrue(content.contains("rss"));
    }
}