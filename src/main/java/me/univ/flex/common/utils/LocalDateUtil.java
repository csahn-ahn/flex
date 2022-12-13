package me.univ.flex.common.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class LocalDateUtil {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_WITH_WEEKDAY_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd(EEE)").withLocale(Locale.US);
    private static final  DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Date asDateFromLocalDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDateFromLocalDateTime(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate asLocalDateFromDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTimeFromDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static String asDateString(LocalDate localDate) {
        return localDate.format(DATE_FORMATTER);
    }

    public static String asDateStringWithWeekday(LocalDate localDate) {
        return localDate.format(DATE_WITH_WEEKDAY_FORMATTER);
    }

    public static String asDateString(LocalDateTime localDateTime) {
        return localDateTime.format(DATE_FORMATTER);
    }

    public static String asDateTimeString(LocalDate localDate) {
        return localDate.format(DATETIME_FORMATTER);
    }

    public static String asDateTimeString(LocalDateTime localDateTime) {
        return localDateTime.format(DATETIME_FORMATTER);
    }

    public static LocalDate asLocalDateFromString(String stringDate) {
        return LocalDate.parse(stringDate, DATE_FORMATTER);
    }

    public static LocalDateTime asLocalDateTimeFromString(String stringDateTime) {
        return LocalDateTime.parse(stringDateTime, DATETIME_FORMATTER);
    }

    public static int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        if ((birthDate != null) && (currentDate != null)) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
    }
}
