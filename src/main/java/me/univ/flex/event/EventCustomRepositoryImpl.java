package me.univ.flex.event;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.univ.flex.admin.manager.QManagerEntity;
import me.univ.flex.common.utils.PageSortUtil;
import me.univ.flex.event.EventEntity.PageRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class EventCustomRepositoryImpl implements EventCustomRepository {
	private final JPAQueryFactory queryFactory;
	private final QEventEntity qEventEntity = QEventEntity.eventEntity;
	private final QEventApplyEntity qEventApplyEntity = QEventApplyEntity.eventApplyEntity;
	private final QManagerEntity qManagerEntity = QManagerEntity.managerEntity;

	@Override
	public Page<EventEntity> findCustomAll(Pageable pageable, PageRequest request) {
		List<EventEntity> list = queryFactory.select(
				Projections.fields(
					EventEntity.class,
					qEventEntity.eventId,
					qEventEntity.title,
					qEventEntity.description,
					qEventEntity.registerId,
					qEventEntity.registerTime,
					qEventEntity.del,
					qManagerEntity.name.as("registerName")
				))
			.from(qEventEntity)
			.leftJoin(qManagerEntity).on(qManagerEntity.username.eq(qEventEntity.registerId))
			.where(
				conditionEventId(request.getEventId()),
				conditionTitle(request.getTitle()),
				qEventEntity.del.eq(false)
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(PageSortUtil.buildOrderSpecifiers(pageable.getSort(), qEventEntity.registerTime))
			.fetch();

		Long total = queryFactory.select(
				qEventEntity.eventId.count()
			)
			.from(qEventEntity)
			.where(
				conditionEventId(request.getEventId()),
				conditionTitle(request.getTitle()),
				qEventEntity.del.eq(false)
			)
			.fetchOne();

		return new PageImpl<>(list, pageable, total);
	}

	private BooleanExpression conditionEventId(int eventId){
		return eventId == 0 ? null : qEventEntity.eventId.eq(eventId);
	}

	private BooleanExpression conditionTitle(String title){
		return StringUtils.isEmpty(title) ? null : qEventEntity.title.contains(title);
	}
}
