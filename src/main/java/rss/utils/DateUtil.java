package rss.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {
    private final static List<String> FORMATS = Arrays.asList(
            "EEE, d MMM yyyy HH:mm:ss z",
            "[EEE,][ ][dd MMM yyyy][ ]['T'][HH:mm:ss][ ][zzz][ZZZ]",
            "yyyy-MM-dd'T'HH:mm:ssZ",
            "dd.MM.yyyy HH:mm:ss Z"
    );
    private final static List<Locale> LOCALES = Arrays.asList(Locale.US, new Locale("ru"));
    private final static Pattern TODAY_PATTERN = Pattern.compile("^\\s*(?:today|сегодня)\\s*(?:in|в)?\\s*(?<h>\\d{2}):(?<m>\\d{2})\\s*$");
    private final static Pattern YESTERDAY_PATTERN = Pattern.compile("^\\s*(?:yesterday|вчера)\\s*(?:in|в)?\\s*(?<h>\\d{2}):(?<m>\\d{2})\\s*$");

    public static ZonedDateTime parse(String stringDate, String format, Locale locale) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, locale);
            return ZonedDateTime.parse(stringDate, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static ZonedDateTime parse(String stringDate, String format) {
        for (Locale locale : LOCALES) {
            ZonedDateTime parsed = parse(stringDate, format, locale);
            if (parsed != null)
                return parsed;
        }
        return null;
    }

    public static ZonedDateTime parse(String stringDate) {
        for (String format : FORMATS) {
            ZonedDateTime parsedDate = parse(stringDate, format);
            if (parsedDate != null)
                return parsedDate;
        }

        Matcher matchToday = TODAY_PATTERN.matcher(stringDate);
        if (matchToday.find()) {
            String time = matchToday.group("h") + ":" + matchToday.group("m");
            LocalTime localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
            ZoneId zone = ZoneId.systemDefault();
            return ZonedDateTime.of(LocalDate.now(zone), localTime, zone);
        }
        Matcher matchYesterday = YESTERDAY_PATTERN.matcher(stringDate);
        if (matchYesterday.find()) {
            String time = matchYesterday.group("h") + ":" + matchYesterday.group("m");
            LocalTime localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
            ZoneId zone = ZoneId.systemDefault();
            return ZonedDateTime.of(LocalDate.now(zone).minusDays(1), localTime, zone);
        }

        return null;
    }
}
