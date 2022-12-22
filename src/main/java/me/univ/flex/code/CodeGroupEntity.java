package me.univ.flex.code;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "code_group")
public class CodeGroupEntity {
    @Id
    @Column(nullable = false)
    private String codeGroupId;
    @Column(nullable = false)
    private String codeGroupName;
    @Column(nullable = false)
    private boolean del;
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy/MM/dd HH:MM", timezone = "Asia/Seoul")
    private Timestamp registerTime;
    @JsonFormat(pattern = "yyyy/MM/dd HH:MM", timezone = "Asia/Seoul")
    private Timestamp lastUpdateTime;
    @JsonFormat(pattern = "yyyy/MM/dd HH:MM", timezone = "Asia/Seoul")
    private Timestamp deleteTime;
    private String registerId;
    private String lastUpdateId;
    private String deleteId;

    @Data
    @Builder
    public static class SearchRequest {
        private int page;
        private int pageSize;
        private String codeGroupId;
        private String codeGroupName;
    }

    @Data
    @Builder
    public static class SaveRequest {
        private String codeGroupId;
        private String codeGroupName;
    }

    @Data
    @Builder
    public static class Response {
        private boolean success;
        private String message;
    }
}
