package me.univ.flex.admin.code;

import me.univ.flex.entity.code.CodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeRepository extends JpaRepository<CodeEntity, String> {
}
