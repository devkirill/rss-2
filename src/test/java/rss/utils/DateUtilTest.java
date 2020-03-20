package rss.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DateUtilTest {
    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "Tue, 17 Mar 2020 16:06:08 GMT;17.03.2020 16:06:08 +0000",
            "Tue, 17 Mar 2020 16:06:08 GMT+06:00;17.03.2020 16:06:08 +0600",
            "Tue, 17 Mar 2020 16:06:08 GMT;17.03.2020 22:06:08 +0600"
    })
    public void parseTest(String firstDate, String secondDate) {
        ZonedDateTime firstParse = DateUtil.parse(firstDate);
        ZonedDateTime secondParse = DateUtil.parse(secondDate);
        assertNotNull(firstParse, String.format("String '%s' not parsed", firstDate));
        assertNotNull(secondParse, String.format("String '%s' not parsed", secondDate));
        assertEquals(firstParse.toEpochSecond(), secondParse.toEpochSecond());
    }
}