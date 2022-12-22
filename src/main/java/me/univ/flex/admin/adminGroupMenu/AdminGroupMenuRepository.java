package me.univ.flex.admin.adminGroupMenu;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminGroupMenuRepository extends JpaRepository<AdminGroupMenuEntity, AdminGroupMenuEntityId>, AdminGroupMenuCustomRepository {
	void deleteByGroupId(int groupId);
}
