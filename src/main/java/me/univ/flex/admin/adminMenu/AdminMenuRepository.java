package me.univ.flex.admin.adminMenu;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminMenuRepository extends JpaRepository<AdminMenuEntity, Integer>, AdminMenuCustomRepository {
	List<AdminMenuEntity> findByUpperMenuId(int upperMenuId);
	boolean existsByUpperMenuIdAndSort(int upperMenuId, int sort);
}
