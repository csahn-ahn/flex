package me.univ.flex.admin.manager;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.univ.flex.entity.adminGroup.QAdminGroupEntity;
import me.univ.flex.entity.manager.ManagerEntity;
import me.univ.flex.entity.manager.QManagerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class ManagerCustomRepositoryImpl implements ManagerCustomRepository {

	private final JPAQueryFactory queryFactory;
	private final QManagerEntity qManagerEntity = QManagerEntity.managerEntity;
	private final QAdminGroupEntity qAdminGroupEntity = QAdminGroupEntity.adminGroupEntity;

	@Override
	public Page<ManagerEntity> findCustomAll(Pageable pageable) {
		List<ManagerEntity> list = queryFactory.select(
				Projections.fields(
					ManagerEntity.class,
					qManagerEntity.username,
					qManagerEntity.name,
					qManagerEntity.hp,
					qManagerEntity.email,
					qManagerEntity.groupId,
					qAdminGroupEntity.groupName,
					qManagerEntity.active,
					qManagerEntity.registerTime,
					qManagerEntity.registerId,
					qManagerEntity.lastLoginTime
				))
			.from(qManagerEntity)
			.leftJoin(qAdminGroupEntity).on(qAdminGroupEntity.groupId.eq(qManagerEntity.groupId))
			.where(
				qManagerEntity.del.isFalse()
			)
			.fetch();

		Long total = queryFactory.select(
				qManagerEntity.username.count()
			)
			.from(qManagerEntity)
			.leftJoin(qAdminGroupEntity).on(qAdminGroupEntity.groupId.eq(qManagerEntity.groupId))
			.where(
				qManagerEntity.del.isFalse()
			)
			.fetchOne();

		return new PageImpl<>(list, pageable, total);
	}
}
