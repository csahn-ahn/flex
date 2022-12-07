INSERT INTO ADMIN_GROUP (group_name, del, register_time) values ('슈퍼관리자', 0, '2022-12-05 00:00:00');
INSERT INTO ADMIN_MENU (menu_name, link_type, link_url, del, register_time) values ('회원관리', 1, '/users', 0, '2022-12-05 00:00:00');
INSERT INTO ADMIN_GROUP_MENU (group_id, menu_id, has_create, has_read, has_update, has_delete, has_download) values (1, 1, 1, 1, 1, 1, 1);