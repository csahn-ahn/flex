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
public class BoardCustomRepositoryImpl implements BoardCustomRepository {
	private final JPAQueryFactory queryFactory;
	private final QBoardEntity qBoardEntity = QBoardEntity.boardEntity;
	private final QManagerEntity qManagerEntity = QManagerEntity.managerEntity;


	@Override
	public Page<BoardEntity> findCustomAll(Pageable pageable, BoardEntity.PageRequest request) {
		List<BoardEntity> list = queryFactory.select(
				Projections.fields(
					BoardEntity.class,
					qBoardEntity.boardId,
					qBoardEntity.boardType,
					qBoardEntity.title,
					qBoardEntity.description,
					qBoardEntity.registerId,
					qBoardEntity.registerTime,
					qBoardEntity.del,
					qManagerEntity.name.as("registerName")
				))
			.from(qBoardEntity)
			.leftJoin(qManagerEntity).on(qManagerEntity.username.eq(qBoardEntity.registerId))
			.where(
				conditionTitle(request.getTitle()),
				conditionBoardType(request.getBoardType()),
				qBoardEntity.del.eq(false)
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(PageSortUtil.buildOrderSpecifiers(pageable.getSort(), qBoardEntity))
			.fetch();

		Long total = queryFactory.select(
				qBoardEntity.boardId.count()
			)
			.from(qBoardEntity)
			.leftJoin(qManagerEntity).on(qManagerEntity.username.eq(qBoardEntity.registerId))
			.where(
				conditionTitle(request.getTitle()),
				conditionBoardType(request.getBoardType()),
				qBoardEntity.del.eq(false)
			)
			.fetchOne();

		return new PageImpl<>(list, pageable, total);
	}

	private BooleanExpression conditionTitle(String title){
		return StringUtils.isEmpty(title) ? null : qBoardEntity.title.contains(title);
	}

	private BooleanExpression conditionBoardType(int contentType){
		return contentType == 0 ? null : qBoardEntity.boardType.eq(contentType);
	}
}
