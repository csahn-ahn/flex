INSERT INTO ADMIN_GROUP (group_name, del, register_time) values ('슈퍼관리자', 0, '2022-12-05 00:00:00');
INSERT INTO ADMIN_GROUP (group_name, del, register_time) values ('대학내일', 0, '2022-12-05 00:00:00');
INSERT INTO ADMIN_GROUP (group_name, del, register_time) values ('운영자', 0, '2022-12-05 00:00:00');

INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (0, '회원', 1, '/admin/user', 'fa-user', 2, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (1, '회원 관리', 1, '/admin/user/users/main', null, 1, 0, '2022-12-05 00:00:00')

INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (0, '참여', 1, '/admin/event', 'fa-gift', 4, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (3, '이벤트-1', 1, '#', null, 1, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (3, '이벤트-2', 1, '#', null, 2, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (3, '이벤트-3', 1, '#', null, 3, 0, '2022-12-05 00:00:00')

INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (0, '코드', 1, '/admin/code', 'fa-barcode', 3, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (7, '공통코드 관리', 1, '/admin/code/common/main', null, 1, 0, '2022-12-05 00:00:00')

INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (0, '시스템', 1, '/admin/system', 'fa-cog', 1, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (9, '운영자 관리', 1, '/admin/system/managers/main', null, 1, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (9, '메뉴 관리', 1, '/admin/system/adminMenus/main', null, 2, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (9, '그룹 관리', 1, '/admin/system/adminGroups/main', null, 3, 0, '2022-12-05 00:00:00')

INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (0, '이력', 1, '/admin/log', 'fa-file-text', 5, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (13, '로그인 이력', 1, '/admin/log/login/main', null, 1, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (13, '접근 이력', 1, '/admin/log/access/main', null, 2, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (13, '다운로드 이력', 1, '/admin/log/download/main', null, 3, 0, '2022-12-05 00:00:00')

insert into admin_group_menu (has_create, has_delete, has_download, has_read, has_update, group_id, menu_id) values (1, 1, 1, 1, 1, 1, 1);
insert into admin_group_menu (has_create, has_delete, has_download, has_read, has_update, group_id, menu_id) values (1, 1, 1, 1, 1, 1, 2);
insert into admin_group_menu (has_create, has_delete, has_download, has_read, has_update, group_id, menu_id) values (1, 1, 1, 1, 1, 1, 3);
insert into admin_group_menu (has_create, has_delete, has_download, has_read, has_update, group_id, menu_id) values (1, 1, 1, 1, 1, 1, 4);
insert into admin_group_menu (has_create, has_delete, has_download, has_read, has_update, group_id, menu_id) values (1, 1, 1, 1, 1, 1, 5);
insert into admin_group_menu (has_create, has_delete, has_download, has_read, has_update, group_id, menu_id) values (1, 1, 1, 1, 1, 1, 6);
insert into admin_group_menu (has_create, has_delete, has_download, has_read, has_update, group_id, menu_id) values (1, 1, 1, 1, 1, 1, 7);
insert into admin_group_menu (has_create, has_delete, has_download, has_read, has_update, group_id, menu_id) values (1, 1, 1, 1, 1, 1, 8);
insert into admin_group_menu (has_create, has_delete, has_download, has_read, has_update, group_id, menu_id) values (1, 1, 1, 1, 1, 1, 9);
insert into admin_group_menu (has_create, has_delete, has_download, has_read, has_update, group_id, menu_id) values (1, 1, 1, 1, 1, 1, 10);
insert into admin_group_menu (has_create, has_delete, has_download, has_read, has_update, group_id, menu_id) values (1, 1, 1, 1, 1, 1, 11);
insert into admin_group_menu (has_create, has_delete, has_download, has_read, has_update, group_id, menu_id) values (1, 1, 1, 1, 1, 1, 12);
insert into admin_group_menu (has_create, has_delete, has_download, has_read, has_update, group_id, menu_id) values (1, 1, 1, 1, 1, 1, 13);
insert into admin_group_menu (has_create, has_delete, has_download, has_read, has_update, group_id, menu_id) values (1, 1, 1, 1, 1, 1, 14);
insert into admin_group_menu (has_create, has_delete, has_download, has_read, has_update, group_id, menu_id) values (1, 1, 1, 1, 1, 1, 15);
insert into admin_group_menu (has_create, has_delete, has_download, has_read, has_update, group_id, menu_id) values (1, 1, 1, 1, 1, 1, 16);

insert into user_stats(year_month, new_count, total_count, leave_count, inactive_count) values ('2022-01', 10, 10, 0, 0);
insert into user_stats(year_month, new_count, total_count, leave_count, inactive_count) values ('2022-02', 10, 10, 0, 0);
insert into user_stats(year_month, new_count, total_count, leave_count, inactive_count) values ('2022-03', 10, 10, 0, 0);
insert into user_stats(year_month, new_count, total_count, leave_count, inactive_count) values ('2022-04', 10, 10, 0, 0);
insert into user_stats(year_month, new_count, total_count, leave_count, inactive_count) values ('2022-05', 10, 10, 0, 0);
insert into user_stats(year_month, new_count, total_count, leave_count, inactive_count) values ('2022-06', 10, 10, 0, 0);
insert into user_stats(year_month, new_count, total_count, leave_count, inactive_count) values ('2022-07', 10, 10, 0, 0);
insert into user_stats(year_month, new_count, total_count, leave_count, inactive_count) values ('2022-08', 10, 10, 0, 0);
insert into user_stats(year_month, new_count, total_count, leave_count, inactive_count) values ('2022-09', 10, 10, 0, 0);
insert into user_stats(year_month, new_count, total_count, leave_count, inactive_count) values ('2022-10', 10, 10, 0, 0);
insert into user_stats(year_month, new_count, total_count, leave_count, inactive_count) values ('2022-11', 10, 10, 0, 0);
insert into user_stats(year_month, new_count, total_count, leave_count, inactive_count) values ('2022-12', 10, 10, 0, 0);