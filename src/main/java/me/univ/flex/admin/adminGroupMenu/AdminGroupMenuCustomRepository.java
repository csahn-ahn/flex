package me.univ.flex.admin.adminGroupMenu;

import java.util.List;
import me.univ.flex.entity.adminGroupMenu.AdminGroupMenuEntity;
import me.univ.flex.entity.manager.ManagerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminGroupMenuCustomRepository {
	List<AdminGroupMenuEntity> findCustomAll(int groupId);
	List<AdminGroupMenuEntity> findMyGroupMenu(int groupId);
}
