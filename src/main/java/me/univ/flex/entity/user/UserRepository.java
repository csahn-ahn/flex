package me.univ.flex.entity.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
	Optional<UserEntity> findBySnsTypeAndSnsUid(String snsType, String snsUid);
}
