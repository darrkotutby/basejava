package by.tut.darrko.webapp.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    public static Date stringToDate(String string) throws ParseException {
        return stringToDate(string, "dd.mm.yyyy");
    }


    public static Date stringToDate(String string, String format) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        return dateFormat.parse(string);
    }

    public static String dateToString(Date date) throws ParseException {
        return dateToString(date, "dd.mm.yyyy");
    }


    public static String dateToString(Date date, String format) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        return dateFormat.format(date);
    }

}
