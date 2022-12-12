package me.univ.flex.admin.stats;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.entity.stats.UserStatsEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserStatsService {
	private final UserStatsMapper userStatsMapper;

	public List<UserStatsEntity> findAll() {
		List<UserStatsEntity> list = userStatsMapper.findAll();
		return list;
	}
}
