package me.univ.flex.admin.adminGroupMenu;

import me.univ.flex.entity.adminGroupMenu.AdminGroupMenuEntity;
import me.univ.flex.entity.adminGroupMenu.AdminGroupMenuEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminGroupMenuRepository extends JpaRepository<AdminGroupMenuEntity, AdminGroupMenuEntityId>, AdminGroupMenuCustomRepository {
	void deleteByGroupId(int groupId);
}
