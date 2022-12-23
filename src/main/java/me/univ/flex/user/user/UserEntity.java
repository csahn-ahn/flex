package me.univ.flex.user.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.utils.MaskingUtils;
import me.univ.flex.common.utils.TimestampUtil;

@Slf4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "users")
public class UserEntity {
    @Id
    @Column(nullable = false)
    private String username;
    @JsonIgnore private String password;
    private String name;
    private String hp;
    private String email;
    private String birth;
    private boolean gender;
    private boolean foreigner;
    private String snsType;
    private String snsUid;
    @JsonIgnore private String tempPassword;
    @Column(nullable = false)
    private boolean del;
    @JsonFormat(pattern = "yyyy/MM/dd HH:MM", timezone = "Asia/Seoul")
    private Timestamp registerTime;
    @JsonFormat(pattern = "yyyy/MM/dd HH:MM", timezone = "Asia/Seoul")
    private Timestamp lastLoginTime;
    @JsonFormat(pattern = "yyyy/MM/dd HH:MM", timezone = "Asia/Seoul")
    private Timestamp lastUpdateTime;
    @JsonFormat(pattern = "yyyy/MM/dd HH:MM", timezone = "Asia/Seoul")
    private Timestamp lastUpdatePasswordTime;
    @JsonFormat(pattern = "yyyy/MM/dd HH:MM", timezone = "Asia/Seoul")
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

    @Data
    public static class PageRequest {
        private int page;
        private int pageSize;
        private String username;
        private String name;
        private String hp;
        private String email;
    }

    @Data
    @Builder
    public static class LoginRequest {
        private String grant;
        private String username;
        private String password;
    }

    @Data
    public static class LoginSnsRequest {
        private String grant;
        private String snsType;
        private String snsUid;
    }

    @Data
    @Builder
    public static class Response {
        private boolean success;
        private String message;
        private Object data;
    }

    @Data
    @Builder
    public static class JoinRequest {
        private String username;
        private String password;
        private String name;
        private String hp;
        private String email;
        private String birth;
        private boolean gender;
        private boolean foreigner;
        private String snsType;
        private String snsUid;
    }

    @Data
    @Builder
    public static class UpdateRequest {
        private String name;
        private String hp;
        private String email;
    }

    @Data
    @Builder
    public static class UpdatePasswordRequest {
        private String password;
        private String newPassword;
    }

    @Data
    @Builder
    public static class LeaveRequest {
        private String password;
        private String temp;
    }

    @Data
    @Builder
    public static class TodayStatsResponse {
        private long totalCount;
        private long todayNewCount;
        private long todayLoginCount;
        private long todayDeleteCount;
    }
}
