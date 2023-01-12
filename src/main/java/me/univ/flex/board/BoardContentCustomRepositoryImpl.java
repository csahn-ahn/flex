package me.univ.flex.board;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.univ.flex.admin.manager.QManagerEntity;
import me.univ.flex.common.utils.PageSortUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class BoardContentCustomRepositoryImpl implements BoardContentCustomRepository {
	private final JPAQueryFactory queryFactory;
	private final QBoardContentEntity qBoardContentEntity = QBoardContentEntity.boardContentEntity;
	private final QManagerEntity qManagerEntity = QManagerEntity.managerEntity;


	@Override
	public Page<BoardContentEntity> findCustomAll(Pageable pageable, BoardContentEntity.PageRequest request) {
		List<BoardContentEntity> list = queryFactory.select(
				Projections.fields(
					BoardContentEntity.class,
					qBoardContentEntity.contentId,
					qBoardContentEntity.boardId,
					qBoardContentEntity.title,
					qBoardContentEntity.body,
					qBoardContentEntity.visible,
					qBoardContentEntity.registerId,
					qBoardContentEntity.registerTime,
					qBoardContentEntity.del,
					qManagerEntity.name.as("registerName")
				))
			.from(qBoardContentEntity)
			.leftJoin(qManagerEntity).on(qManagerEntity.username.eq(qBoardContentEntity.registerId))
			.where(
				conditionTitle(request.getTitle()),
				qBoardContentEntity.del.eq(false),
				qBoardContentEntity.boardId.eq(request.getBoardId())
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(PageSortUtil.buildOrderSpecifiers(pageable.getSort(), qBoardContentEntity))
			.fetch();

		Long total = queryFactory.select(
				qBoardContentEntity.contentId.count()
			)
			.from(qBoardContentEntity)
			.leftJoin(qManagerEntity).on(qManagerEntity.username.eq(qBoardContentEntity.registerId))
			.where(
				conditionTitle(request.getTitle()),
				qBoardContentEntity.del.eq(false),
				qBoardContentEntity.boardId.eq(request.getBoardId())
			)
			.fetchOne();

		return new PageImpl<>(list, pageable, total);
	}

	private BooleanExpression conditionTitle(String title){
		return StringUtils.isEmpty(title) ? null : qBoardContentEntity.title.contains(title);
	}
}
