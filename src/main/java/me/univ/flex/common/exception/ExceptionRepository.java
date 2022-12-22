package me.univ.flex.common.exception;

import me.univ.flex.exception.ExceptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExceptionRepository extends JpaRepository<ExceptionEntity, String> {
}
