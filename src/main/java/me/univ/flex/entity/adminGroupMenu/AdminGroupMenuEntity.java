package me.univ.flex.entity.adminGroupMenu;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
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
}
