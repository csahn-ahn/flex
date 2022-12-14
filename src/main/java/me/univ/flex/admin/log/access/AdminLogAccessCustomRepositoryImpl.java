package me.univ.flex.admin.log.access;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.univ.flex.entity.logs.AdminLogAccessEntity;
import me.univ.flex.entity.logs.QAdminLogAccessEntity;
import me.univ.flex.entity.manager.QManagerEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class AdminLogAccessCustomRepositoryImpl implements AdminLogAccessCustomRepository {

	private final JPAQueryFactory queryFactory;
	private final QAdminLogAccessEntity qAdminLogAccessEntity = QAdminLogAccessEntity.adminLogAccessEntity;
	private final QManagerEntity qManagerEntity = QManagerEntity.managerEntity;

	@Override
	public Page<AdminLogAccessEntity> findCustomAll(Pageable pageable, AdminLogAccessEntity.PageRequest request) {
		List<AdminLogAccessEntity> list = queryFactory.select(
				Projections.fields(
					AdminLogAccessEntity.class,
					qAdminLogAccessEntity.logId,
					qAdminLogAccessEntity.adminId,
					qAdminLogAccessEntity.menuId,
					qAdminLogAccessEntity.menuName,
					qAdminLogAccessEntity.accessUrl,
					qAdminLogAccessEntity.accessTime
				))
			.from(qAdminLogAccessEntity)
			.leftJoin(qManagerEntity).on(qManagerEntity.username.eq(qAdminLogAccessEntity.adminId))
			.where(
				conditionUsername(request.getUsername()),
				conditionName(request.getName())
			)
			.fetch();

		Long total = queryFactory.select(
				qManagerEntity.username.count()
			)
			.from(qAdminLogAccessEntity)
			.leftJoin(qManagerEntity).on(qManagerEntity.username.eq(qAdminLogAccessEntity.adminId))
			.where(
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
		return StringUtils.isNotEmpty(username) ? qAdminLogAccessEntity.adminId.contains(username) : null;
	}

	private BooleanExpression conditionName(String name){
		return StringUtils.isNotEmpty(name) ? qManagerEntity.name.contains(name) : null;
	}


}
