package me.univ.flex.event;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.univ.flex.admin.manager.QManagerEntity;
import me.univ.flex.common.utils.PageSortUtil;
import me.univ.flex.event.EventApplyEntity.PageRequest;
import me.univ.flex.user.user.QUserEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class EventApplyCustomRepositoryImpl implements EventApplyCustomRepository {
	private final JPAQueryFactory queryFactory;
	private final QEventEntity qEventEntity = QEventEntity.eventEntity;
	private final QEventApplyEntity qEventApplyEntity = QEventApplyEntity.eventApplyEntity;
	private final QManagerEntity qManagerEntity = QManagerEntity.managerEntity;
	private final QUserEntity qUserEntity = QUserEntity.userEntity;

	@Override
	public Page<EventApplyEntity> findCustomAll(Pageable pageable, PageRequest request) {
		List<EventApplyEntity> list = queryFactory.select(
				Projections.fields(
					EventApplyEntity.class,
					qEventApplyEntity.applyId,
					qEventApplyEntity.eventId,
					qEventApplyEntity.username,
					qEventApplyEntity.applyTime,
					qEventApplyEntity.etc1,
					qEventApplyEntity.etc2,
					qEventApplyEntity.etc3,
					qUserEntity.name,
					qUserEntity.hp,
					qUserEntity.email
				))
			.from(qEventApplyEntity)
			.leftJoin(qUserEntity).on(qUserEntity.username.eq(qEventApplyEntity.username))
			.where(
				conditionUsername(request.getUsername()),
				conditionName(request.getName())
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(PageSortUtil.buildOrderSpecifiers(pageable.getSort(), qEventApplyEntity.applyTime))
			.fetch();

		Long total = queryFactory.select(
				qEventEntity.eventId.count()
			)
			.from(qEventEntity)
			.where(
				conditionUsername(request.getUsername()),
				conditionName(request.getName())
			)
			.fetchOne();

		return new PageImpl<>(list, pageable, total);
	}

	private BooleanExpression conditionUsername(String username){
		return StringUtils.isEmpty(username) ? null : qEventApplyEntity.username.contains(username);
	}

	private BooleanExpression conditionName(String name){
		return StringUtils.isEmpty(name) ? null : qUserEntity.name.contains(name);
	}
}
