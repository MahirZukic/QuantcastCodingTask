package com.quantcast;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.cli.ParseException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest 
{

    @Test
    public void runWithNoParametersShouldThrowException()
    {
        // GIVEN

        try {
            // WHEN
            App.main(new String[]{});
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
            // THEN
            assertNotNull(e);
        }
    }

    @Test
    public void runWithInsufficientParametersShouldThrowException()
    {
        // GIVEN

        try {
            // WHEN
            App.main(new String[]{"-f", "file.log"});
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
            // THEN
            assertNotNull(e);
        }
    }

    @Test
    public void runWithNonExistingFileShouldThrowException()
    {
        // GIVEN

        try {
            // WHEN
            App.main(new String[]{"-f", "file.log", "-d", "2018-12-09"});
        } catch (IOException e) {
            e.printStackTrace();
            // THEN
            assertNotNull(e);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void runWithBadDateShouldThrowException()
    {
        // GIVEN

        try {
            // WHEN
            App.main(new String[]{"-f", "file.log", "-d", "2018-12-09T12:00:00"});
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            // THEN
            assertNotNull(e);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void runWithGoodDataFirstDateShouldReturnOneResult()
    {
        // GIVEN
        List<App.CookieDate> cookieDates = new ArrayList<App.CookieDate>() {{
            add(new App.CookieDate("AtY0laUfhglK3lC7", LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                    .parse("2018-12-09T14:19:00"))));
            add(new App.CookieDate("SAZuXPGUrfbcn5UA", LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                    .parse("2018-12-09T10:13:00"))));
            add(new App.CookieDate("5UAVanZf6UtGyKVS", LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                    .parse("2018-12-09T07:25:00"))));
            add(new App.CookieDate("AtY0laUfhglK3lC7", LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                    .parse("2018-12-09T06:19:00"))));
            add(new App.CookieDate("SAZuXPGUrfbcn5UA", LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                    .parse("2018-12-08T22:03:00"))));
            add(new App.CookieDate("4sMM2LxV07bPJzwf", LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                    .parse("2018-12-08T21:30:00"))));
            add(new App.CookieDate("fbcn5UAVanZf6UtG", LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                    .parse("2018-12-08T09:30:00"))));
            add(new App.CookieDate("4sMM2LxV07bPJzwf", LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                    .parse("2018-12-07T23:30:00"))));
        }};
        LocalDate givenDate = LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                .parse("2018-12-09T14:19:00"));

        // WHEN
        Map<LocalDate, Map<String, Integer>> cookieCounts = App.getCookieCountsForEachDate(cookieDates);
        List<String> mostRecurringCookieForDate = App.getMostRecurringCookieForDate(cookieCounts, givenDate);

        // THEN
        assertTrue(mostRecurringCookieForDate != null && !mostRecurringCookieForDate.isEmpty());
        assertTrue(mostRecurringCookieForDate.size() == 1);
        assertTrue(mostRecurringCookieForDate.get(0).equalsIgnoreCase("AtY0laUfhglK3lC7"));
    }

    @Test
    public void runWithGoodDataSecondDateShouldReturnThreeResults()
    {
        // GIVEN
        List<App.CookieDate> cookieDates = new ArrayList<App.CookieDate>() {{
            add(new App.CookieDate("AtY0laUfhglK3lC7", LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                    .parse("2018-12-09T14:19:00"))));
            add(new App.CookieDate("SAZuXPGUrfbcn5UA", LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                    .parse("2018-12-09T10:13:00"))));
            add(new App.CookieDate("5UAVanZf6UtGyKVS", LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                    .parse("2018-12-09T07:25:00"))));
            add(new App.CookieDate("AtY0laUfhglK3lC7", LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                    .parse("2018-12-09T06:19:00"))));
            add(new App.CookieDate("SAZuXPGUrfbcn5UA", LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                    .parse("2018-12-08T22:03:00"))));
            add(new App.CookieDate("4sMM2LxV07bPJzwf", LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                    .parse("2018-12-08T21:30:00"))));
            add(new App.CookieDate("fbcn5UAVanZf6UtG", LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                    .parse("2018-12-08T09:30:00"))));
            add(new App.CookieDate("4sMM2LxV07bPJzwf", LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                    .parse("2018-12-07T23:30:00"))));
        }};
        LocalDate givenDate = LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                .parse("2018-12-08T14:19:00"));

        // WHEN
        Map<LocalDate, Map<String, Integer>> cookieCounts = App.getCookieCountsForEachDate(cookieDates);
        List<String> mostRecurringCookieForDate = App.getMostRecurringCookieForDate(cookieCounts, givenDate);

        // THEN
        assertTrue(mostRecurringCookieForDate != null && !mostRecurringCookieForDate.isEmpty());
        assertTrue(mostRecurringCookieForDate.size() == 3);
        assertTrue(mostRecurringCookieForDate.get(0).equalsIgnoreCase("fbcn5UAVanZf6UtG"));
        assertTrue(mostRecurringCookieForDate.get(1).equalsIgnoreCase("SAZuXPGUrfbcn5UA"));
        assertTrue(mostRecurringCookieForDate.get(2).equalsIgnoreCase("4sMM2LxV07bPJzwf"));
    }

    @Test
    public void runWithGoodDataThirdDateShouldReturnZeroResults()
    {
        // GIVEN
        List<App.CookieDate> cookieDates = new ArrayList<App.CookieDate>() {{
            add(new App.CookieDate("AtY0laUfhglK3lC7", LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                    .parse("2018-12-09T14:19:00"))));
            add(new App.CookieDate("SAZuXPGUrfbcn5UA", LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                    .parse("2018-12-09T10:13:00"))));
            add(new App.CookieDate("5UAVanZf6UtGyKVS", LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                    .parse("2018-12-09T07:25:00"))));
            add(new App.CookieDate("AtY0laUfhglK3lC7", LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                    .parse("2018-12-09T06:19:00"))));
            add(new App.CookieDate("SAZuXPGUrfbcn5UA", LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                    .parse("2018-12-08T22:03:00"))));
            add(new App.CookieDate("4sMM2LxV07bPJzwf", LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                    .parse("2018-12-08T21:30:00"))));
            add(new App.CookieDate("fbcn5UAVanZf6UtG", LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                    .parse("2018-12-08T09:30:00"))));
            add(new App.CookieDate("4sMM2LxV07bPJzwf", LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                    .parse("2018-12-07T23:30:00"))));
        }};
        LocalDate givenDate = LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                .parse("2018-12-10T14:19:00"));

        // WHEN
        Map<LocalDate, Map<String, Integer>> cookieCounts = App.getCookieCountsForEachDate(cookieDates);
        List<String> mostRecurringCookieForDate = App.getMostRecurringCookieForDate(cookieCounts, givenDate);

        // THEN
        assertTrue(mostRecurringCookieForDate != null);
        assertTrue(mostRecurringCookieForDate.isEmpty());
    }

    @Test
    public void runWithEmptyFileShouldReturnZeroResults()
    {
        // GIVEN
        List<App.CookieDate> cookieDates = new ArrayList<App.CookieDate>() {{
        }};
        LocalDate givenDate = LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                .parse("2018-12-08T14:19:00"));

        // WHEN
        Map<LocalDate, Map<String, Integer>> cookieCounts = App.getCookieCountsForEachDate(cookieDates);
        List<String> mostRecurringCookieForDate = App.getMostRecurringCookieForDate(cookieCounts, givenDate);

        // THEN
        assertTrue(mostRecurringCookieForDate != null);
        assertTrue(mostRecurringCookieForDate.isEmpty());
    }
}
