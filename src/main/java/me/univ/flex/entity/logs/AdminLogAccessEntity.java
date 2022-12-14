package me.univ.flex.entity.logs;

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

@Slf4j
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "admin_log_access")
public class AdminLogAccessEntity {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int logId;
    private String adminId;
    private int menuId;
    private String menuName;
    private String accessUrl;
    private Timestamp accessTime;

    @Transient
    private String adminName;

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
