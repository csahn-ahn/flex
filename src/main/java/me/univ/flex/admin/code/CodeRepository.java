package me.univ.flex.admin.code;

import java.util.List;
import me.univ.flex.entity.code.CodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CodeRepository extends JpaRepository<CodeEntity, String> {
	List<CodeEntity> findByCodeGroupIdAndDisplayAndDelOrderByRegisterTimeDesc(String codeGroupId, boolean display, boolean del);
	Optional<CodeEntity> findByCodeGroupIdAndCodeId(String codeGroupId, String codeId);
}
