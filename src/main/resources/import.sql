INSERT INTO ADMIN_GROUP (group_name, del, register_time) values ('슈퍼관리자', 0, '2022-12-01 00:00:00');
INSERT INTO ADMIN_GROUP (group_name, del, register_time) values ('대학내일', 0, '2022-12-02 00:00:00');
INSERT INTO ADMIN_GROUP (group_name, del, register_time) values ('운영자', 0, '2022-12-03 00:00:00');

INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (0, '회원', 1, '/admin/user', 'fa-user', 2, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (1, '회원 관리', 1, '/admin/user/users/main', null, 1, 0, '2022-12-05 00:00:00')

INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (0, '참여', 1, '/admin/event', 'fa-gift', 5, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (3, '이벤트-1', 1, '#', null, 1, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (3, '이벤트-2', 1, '#', null, 2, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (3, '이벤트-3', 1, '#', null, 3, 0, '2022-12-05 00:00:00')

INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (0, '코드', 1, '/admin/code', 'fa-barcode', 4, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (7, '공통코드 관리', 1, '/admin/code/common/main', null, 1, 0, '2022-12-05 00:00:00')

INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (0, '시스템', 1, '/admin/system', 'fa-cog', 1, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (9, '운영자 관리', 1, '/admin/system/managers/main', null, 1, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (9, '메뉴 관리', 1, '/admin/system/adminMenus/main', null, 2, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (9, '그룹 관리', 1, '/admin/system/adminGroups/main', null, 3, 0, '2022-12-05 00:00:00')

INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (0, '이력', 1, '/admin/log', 'fa-file-text', 6, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (13, '로그인 이력', 1, '/admin/log/login/main', null, 1, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (13, '접근 이력', 1, '/admin/log/access/main', null, 2, 0, '2022-12-05 00:00:00')

INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (0, '콘텐츠', 1, '/admin/content', 'fa-folder-open', 3, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (17, '콘텐츠 관리', 1, '/admin/content/contents/main', null, 1, 0, '2022-12-05 00:00:00')

INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (0, '외부링크', 1, '/admin/external', 'fa-external-link', 7, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (19, 'inspinia', 2, 'http://webapplayers.com/inspinia_admin-v2.9.4/', null, 1, 0, '2022-12-05 00:00:00')

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
insert into admin_group_menu (has_create, has_delete, has_download, has_read, has_update, group_id, menu_id) values (1, 1, 1, 1, 1, 1, 17);
insert into admin_group_menu (has_create, has_delete, has_download, has_read, has_update, group_id, menu_id) values (1, 1, 1, 1, 1, 1, 18);
insert into admin_group_menu (has_create, has_delete, has_download, has_read, has_update, group_id, menu_id) values (1, 1, 1, 1, 1, 1, 19);
insert into admin_group_menu (has_create, has_delete, has_download, has_read, has_update, group_id, menu_id) values (1, 1, 1, 1, 1, 1, 20);

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

insert into code_group(code_group_id, code_group_name, del, register_time) values ('SAMPLE_CODE_01', '샘플 상위코드 01', 0, '2022-12-11 00:00:00');
insert into code_group(code_group_id, code_group_name, del, register_time) values ('SAMPLE_CODE_02', '샘플 상위코드 02', 0, '2022-12-12 00:00:00');
insert into code_group(code_group_id, code_group_name, del, register_time) values ('SAMPLE_CODE_03', '샘플 상위코드 03', 0, '2022-12-13 00:00:00');

insert into code(code_id, code_group_id, code_value, code_name, display, del, register_time) values ('LOWER_CODE_01', 'SAMPLE_CODE_01', 'A1', '샘플 하위코드 01', 1, 0, '2022-12-11 00:00:00');
insert into code(code_id, code_group_id, code_value, code_name, display, del, register_time) values ('LOWER_CODE_02', 'SAMPLE_CODE_01', 'B2', '샘플 하위코드 02', 1, 0, '2022-12-12 00:00:00');
insert into code(code_id, code_group_id, code_value, code_name, display, del, register_time) values ('LOWER_CODE_03', 'SAMPLE_CODE_01', 'C3', '샘플 하위코드 02', 1, 0, '2022-12-13 00:00:00');

insert into code(code_id, code_group_id, code_value, code_name, display, del, register_time) values ('LOWER_CODE_04', 'SAMPLE_CODE_02', 'A2', '샘플 하위코드 01', 1, 0, '2022-12-11 00:00:00');
insert into code(code_id, code_group_id, code_value, code_name, display, del, register_time) values ('LOWER_CODE_05', 'SAMPLE_CODE_02', 'B2', '샘플 하위코드 02', 1, 0, '2022-12-12 00:00:00');
insert into code(code_id, code_group_id, code_value, code_name, display, del, register_time) values ('LOWER_CODE_06', 'SAMPLE_CODE_02', 'A2', '샘플 하위코드 02', 1, 0, '2022-12-13 00:00:00');

insert into code(code_id, code_group_id, code_value, code_name, display, del, register_time) values ('LOWER_CODE_07', 'SAMPLE_CODE_03', 'A3', '샘플 하위코드 01', 1, 0, '2022-12-11 00:00:00');
insert into code(code_id, code_group_id, code_value, code_name, display, del, register_time) values ('LOWER_CODE_08', 'SAMPLE_CODE_03', 'A3', '샘플 하위코드 02', 1, 0, '2022-12-12 00:00:00');
insert into code(code_id, code_group_id, code_value, code_name, display, del, register_time) values ('LOWER_CODE_09', 'SAMPLE_CODE_03', 'A3', '샘플 하위코드 02', 1, 0, '2022-12-13 00:00:00');