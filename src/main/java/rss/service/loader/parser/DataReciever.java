package rss.service.loader.parser;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class DataReciever {
    public InputStream getContent(String uri) {
        BufferedInputStream buffInputStream = null;

        try {
            URL url = new URL(uri);

            URLConnection urlConnection = url.openConnection();

            buffInputStream = new BufferedInputStream(urlConnection.getInputStream());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

        return buffInputStream;
    }
}
