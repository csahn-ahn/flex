package me.univ.flex.common.utils;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

@Slf4j
public class MaskingUtils {

  private static final String TEMP_MASKING = "**********************";
  private static final String REGEX_EMAIL_SHORT = "(^[^@]{1}|(?!^)\\G)[^@]";
  private static final String REGEX_EMAIL_LONG = "(^[^@]{3}|(?!^)\\G)[^@]";
  private static final String REGEX_TEL_NUMBER = "(^02|^0505|^1[0-9]{3}|^0[0-9]{2})-([0-9]{3,4})-([0-9]{4})";

  private MaskingUtils() {
  }

  public static String getMaskedEmail(String email) {
    if (Strings.isBlank(email)) {
      return "";
    }

    if (email.indexOf("@", 3) > -1) {
      return email.replaceAll(REGEX_EMAIL_LONG, "$1*");
    } else {
      return email.replaceAll(REGEX_EMAIL_SHORT, "$1*");
    }
  }

  public static String getMaskedTel(String tel) {
    if (Strings.isEmpty(tel)) {
      return "";
    }
    Matcher matcher = Pattern.compile(REGEX_TEL_NUMBER).matcher(tel);
    if (matcher.find()) {
      String replaceTarget = matcher.group(3);
      char[] c = new char[replaceTarget.length()];
      Arrays.fill(c, '*');
      return tel.replace(replaceTarget, String.valueOf(c));
    }
    return tel;
  }

  public static String getMaskedPhone(String phone) {
    if (Strings.isEmpty(phone)) {
      return "";
    }
    Matcher matcher = Pattern.compile(REGEX_TEL_NUMBER).matcher(phone);
    if (matcher.find()) {
      String replaceTarget = matcher.group(2);
      char[] c = new char[replaceTarget.length()];
      Arrays.fill(c, '*');
      return phone.replace(replaceTarget, String.valueOf(c));
    }
    return phone;
  }

  public static String getMaskedId(String id) {
    if (Strings.isEmpty(id)) {
      return "";
    }
    if (id == null || id.length() < 2) {
      return id;
    }

    if (id.length() == 2) {
      return id.substring(0, 1) + TEMP_MASKING.substring(0, 1);
    } else if (id.length() == 3) {
      return id.substring(0, 1) + TEMP_MASKING.substring(0, 2);
    } else {
      return id.substring(0, 3) + TEMP_MASKING.substring(0, id.length() - 3);
    }
  }

  public static String getMaskedName(String name) {
    if (Strings.isEmpty(name)) {
      return "";
    }
    if (name == null || name.length() < 2) {
      return name;
    }

    if (name.length() == 2) {
      return name.substring(0, 1) + "*";
    } else {
      return name.substring(0, 1) + "*" + name.substring(2);
    }
  }
}
