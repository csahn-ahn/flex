package me.univ.flex.code;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CodeRepository extends JpaRepository<CodeEntity, String> {
	List<CodeEntity> findByCodeGroupIdAndDisplayAndDelOrderByRegisterTimeDesc(String codeGroupId, boolean display, boolean del);
	Optional<CodeEntity> findByCodeGroupIdAndCodeId(String codeGroupId, String codeId);
}
