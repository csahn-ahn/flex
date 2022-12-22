package me.univ.flex.code;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeGroupRepository extends JpaRepository<CodeGroupEntity, String> {
	List<CodeGroupEntity> findByCodeGroupIdLikeAndCodeGroupNameLikeAndDelOrderByRegisterTimeDesc(String codeGroupId, String codeGroupName, boolean del);
}
