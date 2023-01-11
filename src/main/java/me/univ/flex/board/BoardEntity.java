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
@Table(name = "board")
public class BoardEntity {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int boardId;
    @Column(nullable = false)
    private int boardType;
    @Column(columnDefinition = "nvarchar(1024)", nullable = false)
    private String title;
    @Column(columnDefinition = "nvarchar(4096)", nullable = false)
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
        private int boardId;
        private int boardType;
        private String title;
    }

    @Data
    @Builder
    public static class SaveRequest {
        private int boardId;
        private int boardType;
        private String title;
        private String description;
    }

    @Data
    @Builder
    public static class Response {
        private boolean success;
        private String message;
    }
}
