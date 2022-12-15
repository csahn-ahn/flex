package me.univ.flex.entity.code;

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
@Table(name = "code")
public class CodeEntity {
    @Id
    @Column(nullable = false)
    private String codeId;
    @Column(nullable = false)
    private String codeGroupId;
    @Column(nullable = false)
    private String codeValue;
    private String codeName;
    private String description;
    private String etc1;
    private String etc2;
    private String etc3;
    @Column(nullable = false)
    private boolean display;
    @Column(nullable = false)
    private boolean del;
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy/MM/dd hh:mm")
    private Timestamp registerTime;
    @JsonFormat(pattern = "yyyy/MM/dd hh:mm")
    private Timestamp lastUpdateTime;
    @JsonFormat(pattern = "yyyy/MM/dd hh:mm")
    private Timestamp deleteTime;
    private String registerId;
    private String lastUpdateId;
    private String deleteId;

    @Data
    @Builder
    public static class SaveRequest {
        private String codeId;
        private String codeValue;
        private String codeName;
        private String description;
        private String etc1;
        private String etc2;
        private String etc3;

    }

    @Data
    @Builder
    public static class Response {
        private boolean success;
        private String message;
    }
}
