package me.univ.flex.entity.content;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.utils.TimestampUtil;

@Slf4j
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "content_item")
public class ContentItemEntity {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int itemId;
    @Column(nullable = false)
    private String contentId;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Timestamp serviceStartTime;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
    private Timestamp serviceEndTime;
    private String title;
    private String body;
    private boolean live;
    private boolean preview;
    @Column(nullable = false)
    private boolean del;
    @JsonFormat(pattern = "yyyy/MM/dd hh:mm", timezone = "Asia/Seoul")
    private Timestamp registerTime;
    @JsonFormat(pattern = "yyyy/MM/dd hh:mm", timezone = "Asia/Seoul")
    private Timestamp lastUpdateTime;
    @JsonFormat(pattern = "yyyy/MM/dd hh:mm", timezone = "Asia/Seoul")
    private Timestamp deleteTime;
    private String registerId;
    private String lastUpdateId;
    private String deleteId;

    public boolean getServiceStatus() {
        if(this.serviceStartTime != null && this.serviceEndTime != null) {
            if(this.serviceStartTime.before(TimestampUtil.now())
                && this.serviceEndTime.after(TimestampUtil.now())){
                return true;
            }
        }
        return false;
    }

    @Data
    @Builder
    public static class SaveRequest {
        private int itemId;
        private String contentId;
        private String title;
        private boolean live;
        private boolean preview;
        private String body;
        private String serviceStartTime;
        private String serviceEndTime;
    }

    @Data
    @Builder
    public static class Response {
        private boolean success;
        private String message;
    }
}
