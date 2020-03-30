package rss.utils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class RenderDateUtil {
    private static final String DEFAULT_DATE_FORMAT = "dd.MM.yyyy HH:mm:ss";

    public static String format(ZonedDateTime date, String format, Locale locale)
    {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(format, locale);

        return dateFormat.format(date);
    }

    public static String format(ZonedDateTime date, String format)
    {
        return format(date, format, Locale.US);
    }

    public static String format(ZonedDateTime date)
    {
        return format(date, DEFAULT_DATE_FORMAT);
    }
}
