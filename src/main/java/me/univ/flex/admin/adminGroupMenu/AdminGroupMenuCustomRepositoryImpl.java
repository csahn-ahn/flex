package me.univ.flex.admin.adminGroupMenu;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.univ.flex.entity.adminGroupMenu.AdminGroupMenuEntity;
import me.univ.flex.entity.adminGroupMenu.QAdminGroupMenuEntity;
import me.univ.flex.entity.adminMenu.QAdminMenuEntity;

@RequiredArgsConstructor
class AdminGroupMenuCustomRepositoryImpl implements AdminGroupMenuCustomRepository {

	private final JPAQueryFactory queryFactory;
	private final QAdminGroupMenuEntity qAdminGroupMenuEntity = QAdminGroupMenuEntity.adminGroupMenuEntity;
	private final QAdminMenuEntity qAdminMenu = QAdminMenuEntity.adminMenuEntity;

	@Override
	public List<AdminGroupMenuEntity> findCustomAll(int groupId) {
		return queryFactory.select(
				Projections.fields(
					AdminGroupMenuEntity.class,
					qAdminGroupMenuEntity.groupId,
					qAdminMenu.menuId,
					qAdminMenu.upperMenuId,
					qAdminMenu.menuName,
					qAdminGroupMenuEntity.hasCreate,
					qAdminGroupMenuEntity.hasRead,
					qAdminGroupMenuEntity.hasUpdate,
					qAdminGroupMenuEntity.hasDelete,
					qAdminGroupMenuEntity.hasDownload
				)
			)
			.from(qAdminMenu)
			.leftJoin(qAdminGroupMenuEntity).on(
				(qAdminGroupMenuEntity.menuId).eq(qAdminMenu.menuId)
					.and(qAdminGroupMenuEntity.groupId.eq(groupId))
			)
			.where(qAdminMenu.del.eq(false))
			.orderBy(qAdminMenu.sort.asc())
			.fetch();
	}

	@Override
	public List<AdminGroupMenuEntity> findMyGroupMenu(int groupId) {
		return queryFactory.select(
				Projections.fields(
					AdminGroupMenuEntity.class,
					qAdminGroupMenuEntity.groupId,
					qAdminMenu.menuId,
					qAdminMenu.upperMenuId,
					qAdminMenu.menuName,
					qAdminMenu.linkType,
					qAdminMenu.linkUrl,
					qAdminMenu.icon,
					qAdminMenu.sort,
					qAdminGroupMenuEntity.hasCreate,
					qAdminGroupMenuEntity.hasRead,
					qAdminGroupMenuEntity.hasUpdate,
					qAdminGroupMenuEntity.hasDelete,
					qAdminGroupMenuEntity.hasDownload
				)
			)
			.from(qAdminMenu)
			.innerJoin(qAdminGroupMenuEntity).on(qAdminGroupMenuEntity.menuId.eq(qAdminMenu.menuId))
			.where(
				qAdminGroupMenuEntity.groupId.eq(groupId),
				qAdminMenu.del.eq(false)
			)
			.orderBy(qAdminMenu.sort.asc())
			.fetch();
	}
}
