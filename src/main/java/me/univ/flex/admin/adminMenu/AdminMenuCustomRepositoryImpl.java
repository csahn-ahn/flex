package me.univ.flex.admin.adminMenu;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.univ.flex.entity.adminMenu.QAdminMenuEntity;

@RequiredArgsConstructor
class AdminMenuCustomRepositoryImpl implements AdminMenuCustomRepository {

	private final JPAQueryFactory queryFactory;
	private final QAdminMenuEntity qAdminMenu = QAdminMenuEntity.adminMenuEntity;

	@Override
	public Integer finMenuSortMax(int upperMenuId) {
		return queryFactory.select(
				qAdminMenu.sort.max()
			)
			.from(qAdminMenu)
			.where(
				qAdminMenu.del.eq(false),
				qAdminMenu.upperMenuId.eq(upperMenuId)
			)
			.fetchOne();
	}
}
