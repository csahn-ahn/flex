package me.univ.flex.user.user;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String>, UserCustomRepository {
	Optional<UserEntity> findBySnsTypeAndSnsUid(String snsType, String snsUid);

	long countByDel(boolean del);
	long countByRegisterTimeBetweenAndDel(Timestamp start, Timestamp end, boolean del);
	long countByLastLoginTimeBetweenAndDel(Timestamp start, Timestamp end, boolean del);
	long countByDeleteTimeBetweenAndDel(Timestamp start, Timestamp end, boolean del);
}
