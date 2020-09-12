package com.utnphones.utnPhones.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static String formatDate(String dateOriginal) throws ParseException {
        simpleDateFormat.setLenient(false);
        Date date = simpleDateFormat.parse(dateOriginal);
        return simpleDateFormat.format(date);
    }
}
