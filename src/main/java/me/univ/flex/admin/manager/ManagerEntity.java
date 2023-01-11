package me.univ.flex.admin.manager;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.utils.MaskingUtils;

@Slf4j
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "managers")
public class ManagerEntity {
    @Id
    @Column(nullable = false)
    private String username;
    private String password;
    @Column(nullable = false)
    private String name;
    private String hp;
    private String email;
    @Column(nullable = false)
    private int groupId;
    @Column(nullable = false)
    private boolean active;
    private String tempPassword;
    @Column(nullable = false)
    private boolean del;
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp registerTime;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp lastLoginTime;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp lastUpdateTime;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp lastUpdatePasswordTime;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp deleteTime;
    private String registerId;
    private String lastUpdateId;
    private String deleteId;

    public String getMaskedUsername() {
        return MaskingUtils.getMaskedId(this.username);
    }
    public String getMaskedName() {
        return MaskingUtils.getMaskedName(this.name);
    }
    public String getMaskedEmail() {
        return MaskingUtils.getMaskedEmail(this.email);
    }
    public String getMaskedHp() {
        return MaskingUtils.getMaskedPhone(this.hp);
    }

    @Transient
    private String groupName;

    @Data
    public static class PageRequest {
        private int page;
        private int pageSize;
        private String username;
        private String name;
        private int groupId;
    }

    @Data
    public static class SaveRequest {
        private String username;
        private String password;
        private String name;
        private String hp;
        private String email;
        private int groupId;
    }

    @Data
    public static class DeleteRequest {
        private String username;
    }

    @Data
    @Builder
    public static class Response {
        private boolean success;
        private String message;
    }

    @Data
    @Builder
    public static class UpdatePasswordRequest {
        private String password;
        private String newPassword;
    }
}
