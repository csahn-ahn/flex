package me.univ.flex.user.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String>, UserCustomRepository {
	Optional<UserEntity> findBySnsTypeAndSnsUid(String snsType, String snsUid);
}
