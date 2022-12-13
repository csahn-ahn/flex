package me.univ.flex.common.utils;

import org.springframework.util.AlternativeJdkIdGenerator;

public class FlexGenerator {

  private FlexGenerator() {
  }

  private static final AlternativeJdkIdGenerator alternativeJdkIdGenerator = new AlternativeJdkIdGenerator();

  public static String generate() {
    return generate(32);
  }

  public static String generate(int length) {
    String date = TimestampUtil.nowForUid();

    if (length < 16) {
      return alternativeJdkIdGenerator.generateId().toString()
          .replace("-", "")
          .substring(0, length);
    } else {
      return getCharForNumber(Integer.parseInt(date.substring(0, 3)) - 202)
          + date.charAt(3)
          + getCharForNumber(Integer.parseInt(date.substring(4, 6)))
          + date.substring(6, 8)
          + getCharForNumber(Integer.parseInt(date.substring(8, 10)))
          + date.substring(10, 16)
          + alternativeJdkIdGenerator.generateId().toString()
          .replace("-", "")
          .substring(0, length - 12);
    }
  }

  private static String getCharForNumber(int i) {
    return String.valueOf((char) (i + 'a'));
  }
}
