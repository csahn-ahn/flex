package me.univ.flex.event;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventApplyRepository extends JpaRepository<EventApplyEntity, Integer> {
	int countByEventId(int eventId);
	boolean existsByEventIdAndUsername(int eventId, String username);
}
