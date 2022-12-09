package me.univ.flex.entity.adminGroup;

import java.sql.Timestamp;
import java.util.List;
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
import me.univ.flex.entity.adminGroupMenu.AdminGroupMenuEntity;

@Slf4j
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "admin_group")
public class AdminGroupEntity {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int groupId;
    @Column(nullable = false)
    private String groupName;
    @Column(nullable = false)
    private boolean del;
    @Column(nullable = false)
    private Timestamp registerTime;
    private Timestamp lastUpdateTime;
    private Timestamp deleteTime;
    private String registerId;
    private String lastUpdateId;
    private String deleteId;

    @Transient
    private long managerCount;

    @Data
    @Builder
    public static class SaveRequest {
        private int groupId;
        private String groupName;
    }

    @Data
    @Builder
    public static class DeleteResponse {
        private boolean success;
        private String message;
    }
}
