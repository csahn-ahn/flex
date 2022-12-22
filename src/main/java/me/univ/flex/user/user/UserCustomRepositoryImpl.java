package me.univ.flex.user.user;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.univ.flex.admin.manager.ManagerEntity;
import me.univ.flex.common.utils.PageSortUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {

	private final JPAQueryFactory queryFactory;
	private final QUserEntity qUserEntity = QUserEntity.userEntity;

	@Override
	public Page<UserEntity> findCustomAll(Pageable pageable, UserEntity.PageRequest request) {
		List<UserEntity> list = queryFactory.select(
				Projections.fields(
					UserEntity.class,
					qUserEntity.username,
					qUserEntity.name,
					qUserEntity.hp,
					qUserEntity.email,
					qUserEntity.registerTime,
					qUserEntity.registerId,
					qUserEntity.lastLoginTime
				))
			.from(qUserEntity)
			.where(
				qUserEntity.del.isFalse(),
				conditionUsername(request.getUsername()),
				conditionName(request.getName()),
				conditionHp(request.getHp()),
				conditionEmail(request.getEmail())
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(PageSortUtil.buildOrderSpecifiers(pageable.getSort(), qUserEntity.registerTime))
			.fetch();

		Long total = queryFactory.select(
				qUserEntity.username.count()
			)
			.from(qUserEntity)
			.where(
				qUserEntity.del.isFalse(),
				conditionUsername(request.getUsername()),
				conditionName(request.getName())
			)
			.fetchOne();

		return new PageImpl<>(list, pageable, total);
	}

	private BooleanExpression conditionUsername(String username){
		return StringUtils.isNotEmpty(username) ? qUserEntity.username.contains(username) : null;
	}

	private BooleanExpression conditionName(String name){
		return StringUtils.isNotEmpty(name) ? qUserEntity.name.contains(name) : null;
	}

	private BooleanExpression conditionHp(String hp){
		return StringUtils.isNotEmpty(hp) ? qUserEntity.hp.contains(hp) : null;
	}

	private BooleanExpression conditionEmail(String email){
		return StringUtils.isNotEmpty(email) ? qUserEntity.email.contains(email) : null;
	}
}
