package me.univ.flex.event;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventEntity, Integer>, EventCustomRepository {
}
