package me.univ.flex.admin.logs.access;

import me.univ.flex.entity.logs.AdminLogAccessEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminLogAccessRepository extends JpaRepository<AdminLogAccessEntity, Integer> {
}
