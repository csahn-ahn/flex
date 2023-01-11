package me.univ.flex.event;

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
@Table(name = "event_apply")
public class EventApplyEntity {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int applyId;
    @Column(nullable = false)
    private int eventId;
    @Column(nullable = false)
    private String username;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp applyTime;
    private String etc1;
    private String etc2;
    private String etc3;

    @Transient
    private String name;
    @Transient
    private String email;
    @Transient
    private String hp;

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
    @Builder
    public static class PageRequest {
        private int page;
        private int pageSize;
        private String username;
        private String name;
    }

    @Data
    @Builder
    public static class ApplyRequest {
        private int eventId;
    }

    @Data
    @Builder
    public static class Response {
        private boolean success;
        private String message;
        private Object data;
    }
}
