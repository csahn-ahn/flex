package me.univ.flex.admin.stats;

import java.util.List;
import me.univ.flex.entity.stats.UserStatsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserStatsMapper {
	public List<UserStatsEntity> findAll();
}
