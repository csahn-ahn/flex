package me.univ.flex.common.service.email.thunder;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
public class ThunderMailRequest implements Serializable {

  private String automailIDEncrypt;
  private String mailTitle;
  private String mailContent;
  private String senderName;
  private String senderEmail;
  private String receiverName;
  private String receiverEmail;
  private String returnEmail;
  private String onetooneInfo;
  private String fileIDs;
  private String fileName;
  private String filePath;
  private String fileContent;
  private String customerID;
  private String reserveDate;

  @Builder
  public ThunderMailRequest(String automailIDEncrypt, String mailTitle, String mailContent,
      String senderName, String senderEmail, String receiverEmail,
      String returnEmail) {
    this.automailIDEncrypt = automailIDEncrypt;
    this.mailTitle = mailTitle;
    this.mailContent = mailContent;
    this.senderName = senderName;
    this.senderEmail = senderEmail;
    this.receiverEmail = receiverEmail;
    this.returnEmail = returnEmail;
    this.receiverName = "";
    this.onetooneInfo = "";
    this.fileIDs = "";
    this.fileName = "";
    this.filePath = "";
    this.fileContent = "";
    this.customerID = "";
    this.reserveDate = "";
  }
}
