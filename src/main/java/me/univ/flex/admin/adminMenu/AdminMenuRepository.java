package me.univ.flex.admin.adminMenu;

import java.util.List;
import me.univ.flex.entity.adminMenu.AdminMenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminMenuRepository extends JpaRepository<AdminMenuEntity, Integer>, AdminMenuCustomRepository {
	List<AdminMenuEntity> findByUpperMenuId(int upperMenuId);
	boolean existsByUpperMenuIdAndSort(int upperMenuId, int sort);
}
