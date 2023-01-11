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
@Table(name = "event")
public class EventEntity {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int eventId;
    @Column(nullable = false)
    private String title;
    private String description;
    @Column(nullable = false)
    private boolean del;
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp registerTime;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp lastUpdateTime;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp deleteTime;
    private String registerId;
    private String lastUpdateId;
    private String deleteId;

    @Transient
    private int applyCount;

    @Transient
    private String registerName;

    @Data
    @Builder
    public static class PageRequest {
        private int page;
        private int pageSize;
        private int eventId;
        private String title;
    }

    @Data
    @Builder
    public static class SaveRequest {
        private int eventId;
        private String title;
        private String description;
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
    public static class ApplyRequest {
        private int eventId;
        private String etc1;
        private String etc2;
        private String etc3;
    }
}
