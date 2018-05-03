package by.tut.darrko.webapp.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateUtil {
    public static LocalDate stringToDate(String string) throws ParseException {
        return stringToDate(string, "dd.MM.yyyy");
    }

    public static LocalDate stringToDate(String string, String format) {
        return LocalDate.parse(string, DateTimeFormatter.ofPattern(format));
    }

    public static String dateToString(LocalDate date) {
        return dateToString(date, "dd.MM.yyyy");
    }

    public static String dateToString(LocalDate date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        return date.format(DateTimeFormatter.ofPattern(format));
    }
}
