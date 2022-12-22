package me.univ.flex.admin.stats;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserStatsMapper {
	public List<UserStatsEntity> findAll();
}
