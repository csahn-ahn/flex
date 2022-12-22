package me.univ.flex.admin.adminGroupMenu;

import java.util.List;

public interface AdminGroupMenuCustomRepository {
	List<AdminGroupMenuEntity> findCustomAll(int groupId);
	List<AdminGroupMenuEntity> findMyGroupMenu(int groupId);
}
