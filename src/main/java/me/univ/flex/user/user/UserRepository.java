package me.univ.flex.user.user;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String>, UserCustomRepository {
	Optional<UserEntity> findByUsernameAndDel(String username, boolean del);
	Optional<UserEntity> findBySnsTypeAndSnsUidAndDel(String snsType, String snsUid, boolean del);

	long countByDel(boolean del);
	long countByRegisterTimeBetween(Timestamp start, Timestamp end);
	long countByLastLoginTimeBetween(Timestamp start, Timestamp end);
	long countByDeleteTimeBetweenAndDel(Timestamp start, Timestamp end, boolean del);
}
