package me.univ.flex.common.utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class TimestampUtil {

  private TimestampUtil() {
  }

  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(
      "yyyy-MM-dd");

  private static final DateTimeFormatter DATETIMEMIN_FORMATTER = DateTimeFormatter.ofPattern(
      "yyyy-MM-dd HH:mm");

  private static final DateTimeFormatter DATETIMESEC_FORMATTER = DateTimeFormatter.ofPattern(
      "yyyy-MM-dd HH:mm:ss");

  private static final DateTimeFormatter DATETIME_ALIMTALK_FORMATTER = DateTimeFormatter.ofPattern(
      "yyyyMMddHHmm");

  private static final DateTimeFormatter DATETIME_FILENAME_FORMATTER = DateTimeFormatter.ofPattern(
      "yyyyMMddHHmmss");

  private static final DateTimeFormatter DATETIME_FOR_UID_FORMATTER = DateTimeFormatter.ofPattern(
      "yyyyMMddHHmmssSSSSSS");


  private static final Timestamp OLD_TIME = Timestamp.valueOf(
      LocalDateUtil.asLocalDateTimeFromString("1982-11-14 00:00:00"));

  public static Timestamp now() {
    return Timestamp.from(Instant.now());
  }

  public static String nowForUid() {
    return LocalDateTime.now().format(DATETIME_FOR_UID_FORMATTER);
  }

  public static Timestamp getTimestamp(java.util.Date date) {
    return new Timestamp(date.getTime());
  }

  public static Timestamp asLocalDateTime(LocalDateTime localDateTime) {
    return Timestamp.valueOf(localDateTime);
  }

  public static Timestamp getOldTempTime() {
    return OLD_TIME;
  }

  public static String toDateString(Timestamp timestamp) {
    if (timestamp == null) {
      return "";
    }
    return timestamp.toLocalDateTime().format(DATE_FORMATTER);
  }

  public static String toAlimTalkReserveTimeString(Timestamp timestamp) {
    if (timestamp == null) {
      return "";
    }
    return timestamp.toLocalDateTime().format(DATETIME_ALIMTALK_FORMATTER);
  }

  public static String toDatetimeMinString(Timestamp timestamp) {
    if (timestamp == null) {
      return "";
    }
    return timestamp.toLocalDateTime().format(DATETIMEMIN_FORMATTER);
  }

  public static String toDatetimeSecString(Timestamp timestamp) {
    if (timestamp == null) {
      return "";
    }
    return timestamp.toLocalDateTime().format(DATETIMESEC_FORMATTER);
  }

  public static String toFilenameDatetimeString(Timestamp timestamp) {
    if (timestamp == null) {
      return "";
    }
    return timestamp.toLocalDateTime().format(DATETIME_FILENAME_FORMATTER);
  }

  public static Timestamp addHours(Timestamp timestamp, int delayHours) {
    Timestamp newTimestamp = new Timestamp(timestamp.getTime());
    newTimestamp.setTime(newTimestamp.getTime() + TimeUnit.HOURS.toMillis(delayHours));
    return newTimestamp;
  }

  public static Timestamp addMinutes(Timestamp timestamp, int delayMinutes) {
    Timestamp newTimestamp = new Timestamp(timestamp.getTime());
    newTimestamp.setTime(newTimestamp.getTime() + TimeUnit.MINUTES.toMillis(delayMinutes));
    return newTimestamp;
  }

  public static Timestamp addOneMillis(Timestamp timestamp) {
    Timestamp newTimestamp = new Timestamp(timestamp.getTime());
    newTimestamp.setTime(newTimestamp.getTime() + TimeUnit.MILLISECONDS.toMillis(1));
    return newTimestamp;
  }

  public static Timestamp addMDays(Timestamp timestamp, int delayDays) {
    Timestamp newTimestamp = new Timestamp(timestamp.getTime());
    newTimestamp.setTime(newTimestamp.getTime() + TimeUnit.DAYS.toMillis(delayDays));
    return newTimestamp;
  }
}
