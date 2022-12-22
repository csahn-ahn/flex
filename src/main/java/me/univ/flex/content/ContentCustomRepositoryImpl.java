package me.univ.flex.content;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.univ.flex.admin.manager.QManagerEntity;
import me.univ.flex.content.ContentEntity.PageRequest;
import me.univ.flex.admin.manager.content.QContentEntity;
import me.univ.flex.admin.manager.content.QContentItemEntity;
import me.univ.flex.common.utils.PageSortUtil;
import me.univ.flex.common.utils.TimestampUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class ContentCustomRepositoryImpl implements ContentCustomRepository {
	private final JPAQueryFactory queryFactory;
	private final QContentEntity qContentEntity = QContentEntity.contentEntity;
	private final QManagerEntity qManagerEntity = QManagerEntity.managerEntity;
	private final QContentItemEntity qContentItemEntity = QContentItemEntity.contentItemEntity;

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

	@Override
	public List<ContentItemEntity> findByUrl(ContentEntity.ContentByUrlRequest request) {
		return queryFactory.select(
				Projections.fields(
					ContentItemEntity.class,
					qContentItemEntity.contentId,
					qContentItemEntity.title,
					qContentItemEntity.body,
					qContentItemEntity.itemId
				)
			)
			.from(qContentEntity)
			.innerJoin(qContentItemEntity).on(qContentItemEntity.contentId.eq(qContentEntity.contentId))
			.where(
				qContentEntity.del.isFalse(),
				qContentItemEntity.serviceStartTime.before(TimestampUtil.now()).and(qContentItemEntity.serviceEndTime.after(TimestampUtil.now())),
				conditionPreview(request.isPreview()),
				conditionUrl(request.getUrl()),
				conditionContentType(request.getContentType())
			)
			.orderBy(qContentItemEntity.itemId.desc())
			.fetch();
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

	private BooleanExpression conditionPreview(boolean preview){
		return preview ? qContentItemEntity.preview.isTrue() : qContentItemEntity.live.isTrue();
	}

	private BooleanExpression conditionContentType(int contentType){
		return contentType == 0 ? null : qContentEntity.contentType.eq(contentType);
	}
}
