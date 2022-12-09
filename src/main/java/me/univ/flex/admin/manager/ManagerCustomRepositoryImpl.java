package me.univ.flex.admin.manager;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.univ.flex.entity.adminGroup.QAdminGroupEntity;
import me.univ.flex.entity.manager.ManagerEntity;
import me.univ.flex.entity.manager.QManagerEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class ManagerCustomRepositoryImpl implements ManagerCustomRepository {

	private final JPAQueryFactory queryFactory;
	private final QManagerEntity qManagerEntity = QManagerEntity.managerEntity;
	private final QAdminGroupEntity qAdminGroupEntity = QAdminGroupEntity.adminGroupEntity;

	@Override
	public Page<ManagerEntity> findCustomAll(Pageable pageable, ManagerEntity.PageRequest request) {
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
				qManagerEntity.del.isFalse(),
				conditionGroupId(request.getGroupId()),
				conditionUsername(request.getUsername()),
				conditionName(request.getName())
			)
			.fetch();

		Long total = queryFactory.select(
				qManagerEntity.username.count()
			)
			.from(qManagerEntity)
			.leftJoin(qAdminGroupEntity).on(qAdminGroupEntity.groupId.eq(qManagerEntity.groupId))
			.where(
				qManagerEntity.del.isFalse(),
				conditionGroupId(request.getGroupId()),
				conditionUsername(request.getUsername()),
				conditionName(request.getName())
			)
			.fetchOne();

		return new PageImpl<>(list, pageable, total);
	}

	private BooleanExpression conditionGroupId(int groupId){
		return groupId > 0 ? qManagerEntity.groupId.eq(groupId) : null;
	}

	private BooleanExpression conditionUsername(String username){
		return StringUtils.isNotEmpty(username) ? qManagerEntity.username.contains(username) : null;
	}

	private BooleanExpression conditionName(String name){
		return StringUtils.isNotEmpty(name) ? qManagerEntity.name.contains(name) : null;
	}
}
