package me.univ.flex.common.properties;

import lombok.Data;

@Data
public class EmailProperties {
  private String apiUrl;
  private String apiKey;
  private String adminUrl;
  private String adminSenderName;
  private String adminSenderEmail;
  private String userUrl;
  private String userSenderName;
  private String userSenderEmail;
}
