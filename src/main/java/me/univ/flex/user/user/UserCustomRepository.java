package me.univ.flex.user.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserCustomRepository {
	Page<UserEntity> findCustomAll(Pageable pageable, UserEntity.PageRequest request);
}
