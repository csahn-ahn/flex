package me.univ.flex.admin.log.login;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.univ.flex.common.utils.PageSortUtil;
import me.univ.flex.entity.logs.AdminLogAccessEntity;
import me.univ.flex.entity.logs.AdminLogLoginEntity;
import me.univ.flex.entity.logs.QAdminLogLoginEntity;
import me.univ.flex.entity.manager.QManagerEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class AdminLogLoginCustomRepositoryImpl implements AdminLogLoginCustomRepository {

	private final JPAQueryFactory queryFactory;
	private final QAdminLogLoginEntity qAdminLogLoginEntity = QAdminLogLoginEntity.adminLogLoginEntity;
	private final QManagerEntity qManagerEntity = QManagerEntity.managerEntity;

	@Override
	public Page<AdminLogLoginEntity> findCustomAll(Pageable pageable, AdminLogLoginEntity.PageRequest request) {
		List<AdminLogLoginEntity> list = queryFactory.select(
				Projections.fields(
					AdminLogLoginEntity.class,
					qAdminLogLoginEntity.logId,
					qAdminLogLoginEntity.adminId,
					qAdminLogLoginEntity.ip,
					qAdminLogLoginEntity.loginTime,
					qManagerEntity.username.as("adminName")
				))
			.from(qAdminLogLoginEntity)
			.leftJoin(qManagerEntity).on(qManagerEntity.username.eq(qAdminLogLoginEntity.adminId))
			.where(
				conditionUsername(request.getUsername()),
				conditionName(request.getName())
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(PageSortUtil.buildOrderSpecifiers(pageable.getSort(), qAdminLogLoginEntity.loginTime))
			.fetch();

		Long total = queryFactory.select(
				qManagerEntity.username.count()
			)
			.from(qAdminLogLoginEntity)
			.leftJoin(qManagerEntity).on(qManagerEntity.username.eq(qAdminLogLoginEntity.adminId))
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
		return StringUtils.isNotEmpty(username) ? qAdminLogLoginEntity.adminId.contains(username) : null;
	}

	private BooleanExpression conditionName(String name){
		return StringUtils.isNotEmpty(name) ? qManagerEntity.name.contains(name) : null;
	}


}
