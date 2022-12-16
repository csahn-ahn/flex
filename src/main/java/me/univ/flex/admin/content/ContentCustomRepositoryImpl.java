package me.univ.flex.admin.content;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.univ.flex.common.utils.PageSortUtil;
import me.univ.flex.entity.content.ContentEntity;
import me.univ.flex.entity.content.ContentEntity.PageRequest;
import me.univ.flex.entity.content.QContentEntity;
import me.univ.flex.entity.manager.QManagerEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class ContentCustomRepositoryImpl implements ContentCustomRepository {
	private final JPAQueryFactory queryFactory;
	private final QContentEntity qContentEntity = QContentEntity.contentEntity;
	private final QManagerEntity qManagerEntity = QManagerEntity.managerEntity;

	@Override
	public Page<ContentEntity> findCustomAll(Pageable pageable, PageRequest request) {
		List<ContentEntity> list = queryFactory.select(
				Projections.fields(
					ContentEntity.class,
					qContentEntity.contentId,
					qContentEntity.contentType,
					qContentEntity.title,
					qContentEntity.description,
					qContentEntity.url,
					qContentEntity.registerId,
					qContentEntity.registerTime,
					qContentEntity.del,
					qManagerEntity.name.as("registerName")
				))
			.from(qContentEntity)
			.leftJoin(qManagerEntity).on(qManagerEntity.username.eq(qContentEntity.registerId))
			.where(
				conditionContentId(request.getContentId()),
				conditionTitle(request.getTitle()),
				conditionUrl(request.getUrl()),
				qContentEntity.del.eq(false)
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(PageSortUtil.buildOrderSpecifiers(pageable.getSort(), qContentEntity.registerTime))
			.fetch();

		Long total = queryFactory.select(
				qContentEntity.contentId.count()
			)
			.from(qContentEntity)
			.where(
				conditionContentId(request.getContentId()),
				conditionTitle(request.getTitle()),
				conditionUrl(request.getUrl()),
				qContentEntity.del.eq(false)
			)
			.fetchOne();

		return new PageImpl<>(list, pageable, total);
	}

	private BooleanExpression conditionContentId(String contentId){
		return StringUtils.isEmpty(contentId) ? null : qContentEntity.contentId.eq(contentId);
	}

	private BooleanExpression conditionTitle(String title){
		return StringUtils.isEmpty(title) ? null : qContentEntity.title.contains(title);
	}

	private BooleanExpression conditionUrl(String url){
		return StringUtils.isEmpty(url) ? null : qContentEntity.url.contains(url);
	}
}
