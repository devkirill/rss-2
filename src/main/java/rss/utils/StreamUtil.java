package rss.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamUtil {
    public static String readStream(InputStream stream) {
        InputStreamReader isReader = new InputStreamReader(stream);

        StringBuilder result = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(isReader);
            String str;
            while ((str = reader.readLine()) != null) {
                result.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }
}
