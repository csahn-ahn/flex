package me.univ.flex.admin.manager;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.univ.flex.entity.adminGroup.QAdminGroupEntity;
import me.univ.flex.entity.manager.ManagerEntity;
import me.univ.flex.entity.manager.QManagerEntity;

@RequiredArgsConstructor
public class ManagerCustomRepositoryImpl implements ManagerCustomRepository {

	private final JPAQueryFactory queryFactory;
	private final QManagerEntity qManagerEntity = QManagerEntity.managerEntity;
	private final QAdminGroupEntity qAdminGroupEntity = QAdminGroupEntity.adminGroupEntity;

	@Override
	public List<ManagerEntity> findCustomAll() {
		return queryFactory.select(
				Projections.fields(
					ManagerEntity.class,
					qManagerEntity.username,
					qManagerEntity.name,
					qManagerEntity.groupId,
					qAdminGroupEntity.groupName,
					qManagerEntity.active,
					qManagerEntity.registerTime,
					qManagerEntity.registerId
				))
			.from(qManagerEntity)
			.leftJoin(qAdminGroupEntity).on(qAdminGroupEntity.groupId.eq(qManagerEntity.groupId))
			.where(
				qManagerEntity.del.isFalse()
			)
			.fetch();
	}
}
