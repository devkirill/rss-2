package rss.utils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class DateUtil {
    private final static List<String> FORMATS = Arrays.asList(
            "EEE, d MMM yyyy HH:mm:ss z",
            "yyyy-MM-dd'T'HH:mm:ssZ",
            "dd.MM.yyyy HH:mm:ss Z"
    );

    public static ZonedDateTime parse(String stringDate, String format) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, Locale.US);
            return ZonedDateTime.parse(stringDate, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static ZonedDateTime parse(String stringDate) {
        for (String format : FORMATS) {
            ZonedDateTime parsedDate = parse(stringDate, format);
            if (parsedDate != null)
                return parsedDate;
        }

        return null;
    }
}
