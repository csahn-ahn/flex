package me.univ.flex.admin.adminGroup;

import me.univ.flex.entity.adminGroup.AdminGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminGroupRepository extends JpaRepository<AdminGroupEntity, Integer> {
}
