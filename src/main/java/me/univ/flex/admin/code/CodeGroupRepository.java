package me.univ.flex.admin.code;

import java.util.List;
import me.univ.flex.entity.code.CodeGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeGroupRepository extends JpaRepository<CodeGroupEntity, String> {
	List<CodeGroupEntity> findByCodeGroupIdLikeAndCodeGroupNameLikeAndDelOrderByRegisterTimeDesc(String codeGroupId, String codeGroupName, boolean del);
}
