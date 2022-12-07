package me.univ.flex.admin.adminMenu;

import me.univ.flex.admin.manager.ManagerCustomRepository;
import me.univ.flex.entity.adminMenu.AdminMenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminMenuRepository extends JpaRepository<AdminMenuEntity, Integer> {
}
