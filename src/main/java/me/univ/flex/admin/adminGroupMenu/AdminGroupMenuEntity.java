package me.univ.flex.admin.adminGroupMenu;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
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
@IdClass(AdminGroupMenuEntityId.class)
@Table(name = "admin_group_menu")
public class AdminGroupMenuEntity {
    @Id
    @Column(nullable = false)
    private int groupId;
    @Id
    @Column(nullable = false)
    private int menuId;
    @Column(nullable = false)
    private boolean hasCreate;
    @Column(nullable = false)
    private boolean hasRead;
    @Column(nullable = false)
    private boolean hasUpdate;
    @Column(nullable = false)
    private boolean hasDelete;
    @Column(nullable = false)
    private boolean hasDownload;

    @Transient
    private String menuName;
    @Transient
    private int upperMenuId;
    @Transient
    private int linkType;
    @Transient
    private String linkUrl;
    @Transient
    private String icon;
    @Transient
    private int sort;

    @Transient
    private boolean active;

    @Transient
    private List<AdminGroupMenuEntity> lowerMenus;

    @Data
    @Builder
    public static class AdminGroupMenuRequest {
        private int groupId;
        private List<AdminGroupMenuEntity> adminGroupMenuList;
    }

    @Data
    @Builder
    public static class AdminGroupMenuResponse {
        private boolean success;
        private String message;
    }
}
