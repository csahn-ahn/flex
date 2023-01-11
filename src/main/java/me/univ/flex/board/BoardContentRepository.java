package me.univ.flex.board;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardContentRepository extends JpaRepository<BoardContentEntity, Integer>, BoardContentCustomRepository {
}
