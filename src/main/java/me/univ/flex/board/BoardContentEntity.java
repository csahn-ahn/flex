package me.univ.flex.board;

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
@Table(name = "board_content")
public class BoardContentEntity {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int contentId;
    @Column(nullable = false)
    private int boardId;
    @Column(columnDefinition = "nvarchar(1024)", nullable = false)
    private String title;
    @Column(columnDefinition = "nvarchar(max)")
    private String body;
    @Column(nullable = false)
    private boolean visible;
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
    private String registerName;

    public String getMaskedUsername() {
        return MaskingUtils.getMaskedId(this.registerId);
    }
    public String getMaskedName() { return MaskingUtils.getMaskedName(this.registerName); }

    @Data
    @Builder
    public static class PageRequest {
        private int page;
        private int pageSize;
        private int boardId;
        private String registerId;
        private String title;
    }

    @Data
    @Builder
    public static class SaveRequest {
        private int contentId;
        private String title;
        private String body;
    }

    @Data
    @Builder
    public static class Response {
        private boolean success;
        private String message;
    }
}
