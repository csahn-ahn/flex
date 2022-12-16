package me.univ.flex.entity.content;

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
@Table(name = "content")
public class ContentEntity {
    @Id
    @Column(nullable = false)
    private String contentId;
    @Column(nullable = false)
    private int contentType;
    @Column(nullable = false)
    private String title;
    private String description;
    private String url;
    @Column(nullable = false)
    private boolean del;
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy/MM/dd hh:mm", timezone = "Asia/Seoul")
    private Timestamp registerTime;
    @JsonFormat(pattern = "yyyy/MM/dd hh:mm", timezone = "Asia/Seoul")
    private Timestamp lastUpdateTime;
    @JsonFormat(pattern = "yyyy/MM/dd hh:mm", timezone = "Asia/Seoul")
    private Timestamp deleteTime;
    private String registerId;
    private String lastUpdateId;
    private String deleteId;

    @Transient
    private String registerName;

    public String getMaskedId() {
        return MaskingUtils.getMaskedId(this.registerId);
    }
    public String getMaskedRegisterName() { return MaskingUtils.getMaskedName(this.registerName); }

    @Data
    @Builder
    public static class PageRequest {
        private int page;
        private int pageSize;
        private String contentId;
        private String title;
        private String url;
    }

    @Data
    @Builder
    public static class SaveRequest {
        private String contentId;
        private int contentType;
        private String title;
        private String description;
        private String url;
    }

    @Data
    @Builder
    public static class Response {
        private boolean success;
        private String message;
    }
}
