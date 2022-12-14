package me.univ.flex.admin.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "admin_log_login")
public class AdminLogLoginEntity {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int logId;
    private String adminId;
    private String ip;

    @JsonFormat(pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp loginTime;

    @Transient
    private String adminName;
    public String getMaskedName() { return MaskingUtils.getMaskedName(this.adminName); }
    public String getMaskedAdminId() { return MaskingUtils.getMaskedId(this.adminId); }

    @Data
    @Builder
    public static class PageRequest {
        private int page;
        private int pageSize;
        private String username;
        private String name;
        private String menuName;
    }
}
