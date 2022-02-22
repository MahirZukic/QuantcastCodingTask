package com.quantcast;

import org.apache.commons.cli.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

public class App
{

    public static final String INPUT_FILE_PARAMETER = "inputFile";
    public static final String INPUT_DATE_PARAMETER = "inputDate";
    public static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ISO_INSTANT;
    public static final String COMMA_DELIMITER = ",";
    public static final java.util.logging.Logger log =
            java.util.logging.Logger.getLogger(App.class.getName());


    public static class CookieDate {
        private String cookie;
        private LocalDate date;

        public CookieDate(String cookie, LocalDate date) {
            this.cookie = cookie;
            this.date = date;
        }

        public String getCookie() {
            return cookie;
        }

        public void setCookie(String cookie) {
            this.cookie = cookie;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }
    }

    private static Map<LocalDate, Map<String, Integer>> cookieCounts = new HashMap<>();

    public static void main( String[] args ) throws IOException, ParseException, DateTimeParseException {
        CommandLine commandLineParameters = getCommandLineParameters(args);
        String fileParam = commandLineParameters.getOptionValue(INPUT_FILE_PARAMETER);
        String dateParam = commandLineParameters.getOptionValue(INPUT_DATE_PARAMETER);
        LocalDate date = LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(dateParam));

        List<CookieDate> cookieDates = extractCookiesFromFile(fileParam);
        Map<LocalDate, Map<String, Integer>> cookieCountsPerDay = getCookieCountsForEachDate(cookieDates);
        List<String> cookiesForGivenDate = getMostRecurringCookieForDate(cookieCountsPerDay, date);
        cookiesForGivenDate.forEach(cookie -> System.out.println(cookie));
    }

    public static CommandLine getCommandLineParameters(String[] args) throws ParseException {
        Options options = new Options();

        Option fileInput = new Option("f", INPUT_FILE_PARAMETER, true, "input log file path");
        fileInput.setRequired(true);
        options.addOption(fileInput);

        Option dateInput = new Option("d", INPUT_DATE_PARAMETER, true, "input date for cookies");
        dateInput.setRequired(true);
        options.addOption(dateInput);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("java -jar 'jar_file'", options);

            throw e;
        }
        return cmd;
    }

    public static List<CookieDate> extractCookiesFromFile(String fileParam) throws IOException {
        log.info("Started processing cookies from file: " + fileParam);
        List<CookieDate> result = new ArrayList<>();
        try {
            FileReader filereader = new FileReader(fileParam);
            BufferedReader br = new BufferedReader(filereader);
            // used to skip the csv header on the first line
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] lineParts = line.split(COMMA_DELIMITER);
                LocalDate localDate = LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                        .parse(lineParts[1].split("\\+")[0]));
                result.add(new CookieDate(lineParts[0], localDate));
            }
        } catch (IOException exception) {
            log.log(Level.SEVERE, "An error occurred reading the file. " + fileParam, exception.getMessage());
            throw exception;
        }
        return result;
    }

    public static Map<LocalDate, Map<String, Integer>> getCookieCountsForEachDate(List<CookieDate> cookieDates) {
        log.info("Started constructing cookies count map from previous data.");
        Map<LocalDate, Map<String, Integer>> result = new HashMap<>();
        cookieDates.forEach(cookieDate -> {
            Map<String, Integer> mapForDate = result.getOrDefault(cookieDate.getDate(), new HashMap<>());
            Integer countOfOccurencesForCookie = mapForDate.getOrDefault(cookieDate.getCookie(), 0);
            countOfOccurencesForCookie++;
            mapForDate.put(cookieDate.getCookie(), countOfOccurencesForCookie);
            result.put(cookieDate.getDate(), mapForDate);
        });

        return result;
    }

    public static List<String> getMostRecurringCookieForDate(Map<LocalDate, Map<String, Integer>> cookieCounts, LocalDate date) {
        log.info("Getting most occurring cookies from previous map.");
        List<String> result = new ArrayList<>();
        Map<String, Integer> cookieCountsForDate = cookieCounts.get(date);
        AtomicInteger max = new AtomicInteger(Integer.MIN_VALUE);
        if (cookieCounts.isEmpty() || cookieCountsForDate == null || cookieCountsForDate.isEmpty()) {
            return result;
        }
        cookieCountsForDate.keySet().forEach(key -> {
            if (cookieCountsForDate.get(key) > max.get()) {
                max.set(cookieCountsForDate.get(key));
            }
        });
        cookieCountsForDate.keySet().forEach(key -> {
            if (cookieCountsForDate.get(key) == max.get()) {
                result.add(key);
            }
        });

        return result;
    }

}
