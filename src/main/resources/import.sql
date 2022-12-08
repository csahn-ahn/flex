INSERT INTO ADMIN_GROUP (group_name, del, register_time) values ('슈퍼관리자', 0, '2022-12-05 00:00:00');
INSERT INTO ADMIN_GROUP (group_name, del, register_time) values ('대학내일', 0, '2022-12-05 00:00:00');
INSERT INTO ADMIN_GROUP (group_name, del, register_time) values ('운영자', 0, '2022-12-05 00:00:00');

INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, sort, del, register_time) values (0, '회원', 1, '/admin/user', 1, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, sort, del, register_time) values (1, '회원 관리', 1, '/admin/user/users/main', 1, 0, '2022-12-05 00:00:00')

INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, sort, del, register_time) values (0, '참여', 1, '/admin/event', 2, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, sort, del, register_time) values (3, '이벤트-1', 1, '#', 1, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, sort, del, register_time) values (3, '이벤트-2', 1, '#', 2, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, sort, del, register_time) values (3, '이벤트-3', 1, '#', 3, 0, '2022-12-05 00:00:00')

INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, sort, del, register_time) values (0, '코드', 1, '/admin/code', 3, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, sort, del, register_time) values (7, '공통코드 관리', 1, '/admin/code/common/main', 1, 0, '2022-12-05 00:00:00')

INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, sort, del, register_time) values (0, '시스템', 1, '/admin/system', 4, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, sort, del, register_time) values (9, '운영자 관리', 1, '/admin/system/managers/main', 1, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, sort, del, register_time) values (9, '메뉴 관리', 1, '/admin/system/adminMenus/main', 2, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, sort, del, register_time) values (9, '그룹 관리', 1, '/admin/system/adminGroups/main', 3, 0, '2022-12-05 00:00:00')

INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, sort, del, register_time) values (0, '이력', 1, '/admin/log', 5, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, sort, del, register_time) values (13, '로그인 이력', 1, '/admin/log/login/main', 1, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, sort, del, register_time) values (13, '접근 이력', 1, '/admin/log/access/main', 2, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, sort, del, register_time) values (13, '다운로드 이력', 1, '/admin/log/download/main', 3, 0, '2022-12-05 00:00:00')

INSERT INTO ADMIN_GROUP_MENU (group_id, menu_id, has_create, has_read, has_update, has_delete, has_download) values (1, 1, 1, 1, 1, 1, 1);