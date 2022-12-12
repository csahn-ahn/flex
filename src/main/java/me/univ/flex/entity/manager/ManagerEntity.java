package me.univ.flex.entity.manager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
import me.univ.flex.entity.adminGroupMenu.AdminGroupMenuEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "managers")
public class ManagerEntity {
    @Id
    @Column(nullable = false)
    private String username;
    @Column()
    private String password;
    @Column(nullable = false)
    private String name;
    private String hp;
    private String email;
    @Column(nullable = false)
    private int groupId;
    @Column(nullable = false)
    private boolean active;
    @Column(nullable = false)
    private boolean tempPassword;
    @Column(nullable = false)
    private boolean del;
    @Column(nullable = false)
    private Timestamp registerTime;
    private Timestamp lastLoginTime;
    private Timestamp lastUpdateTime;
    private Timestamp lastUpdatePasswordTime;
    private Timestamp deleteTime;
    private String registerId;
    private String lastUpdateId;
    private String deleteId;

    @Transient
    private String groupName;

    @Data
    public static class PageRequest {
        private int page;
        private int pageSize;
        private String username;
        private String name;
        private int groupId;
    }

    @Data
    public static class SaveRequest {
        private String username;
        private String password;
        private String name;
        private String hp;
        private String email;
        private int groupId;
    }

    @Data
    public static class DeleteRequest {
        private String username;
    }

    @Data
    @Builder
    public static class DeleteResponse {
        private boolean success;
        private String message;
    }
}
