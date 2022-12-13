package me.univ.flex.common.service.email.thunder;

import java.io.Serializable;
import lombok.Data;

@Data
public class ThunderMailResponse implements Serializable {

  private String code;
  private String msg;

  public ThunderMailResponse() {
    this.code = "";
    this.msg = "";
  }

}
