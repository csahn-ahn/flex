INSERT INTO ADMIN_GROUP (group_name, del, register_time) values ('슈퍼관리자', 0, '2022-12-01 00:00:00');
INSERT INTO ADMIN_GROUP (group_name, del, register_time) values ('대학내일', 0, '2022-12-02 00:00:00');
INSERT INTO ADMIN_GROUP (group_name, del, register_time) values ('운영자', 0, '2022-12-03 00:00:00');

INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (0, '시스템', 1, '/admin/system', 'fa-cog', 1, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (0, '회원', 1, '/admin/user', 'fa-user', 2, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (0, '콘텐츠', 1, '/admin/content', 'fa-folder-open', 3, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (0, '게시판', 1, '/admin/board', 'fa-folder-open', 4, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (0, '참여', 1, '/admin/event', 'fa-gift', 5, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (0, '코드', 1, '/admin/code', 'fa-barcode', 6, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (0, '이력', 1, '/admin/log', 'fa-file-text', 7, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (0, '외부링크', 1, '/admin/external', 'fa-external-link', 8, 0, '2022-12-05 00:00:00')

INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (1, '운영자 관리', 1, '/admin/system/managers/main', null, 1, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (1, '메뉴 관리', 1, '/admin/system/adminMenus/main', null, 2, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (1, '그룹 관리', 1, '/admin/system/adminGroups/main', null, 3, 0, '2022-12-05 00:00:00')

INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (2, '회원 관리', 1, '/admin/user/users/main', null, 1, 0, '2022-12-05 00:00:00')

INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (3, '콘텐츠 관리', 1, '/admin/content/contents/main', null, 1, 0, '2022-12-05 00:00:00')

INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (4, '게시판 관리', 1, '/admin/board/boards/main', null, 1, 0, '2022-12-05 00:00:00')

INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (5, '이벤트관리', 1, '/admin/event/events/main', null, 1, 0, '2022-12-05 00:00:00')

INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (6, '공통코드 관리', 1, '/admin/code/common/main', null, 1, 0, '2022-12-05 00:00:00')

INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (7, '로그인 이력', 1, '/admin/log/login/main', null, 1, 0, '2022-12-05 00:00:00')
INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (7, '접근 이력', 1, '/admin/log/access/main', null, 2, 0, '2022-12-05 00:00:00')

INSERT INTO ADMIN_MENU (upper_menu_id, menu_name, link_type, link_url, icon, sort, del, register_time) values (8, 'inspinia', 2, 'http://webapplayers.com/inspinia_admin-v2.9.4/', null, 1, 0, '2022-12-05 00:00:00')




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

insert into content(content_id, content_type, title, url, del, register_time) values ('TEST_CONTENT_01', 1, '테스트 콘텐츠-1', null, false, '2022-12-01 00:00:00');
insert into content(content_id, content_type, title, url, del, register_time) values ('TEST_CONTENT_02', 2, '테스트 콘텐츠-1', '/user/main', false, '2022-12-02 00:00:00');
insert into content(content_id, content_type, title, url, del, register_time) values ('TEST_CONTENT_03', 1, '테스트 콘텐츠-1', null, false, '2022-12-03 00:00:00');
insert into content_item(content_id, service_start_time, service_end_time, title, body, live, preview, del, register_time) values ('TEST_CONTENT_02', '2022-12-01 00:00:00', '2022-12-30 00:00:00', '아이템 1', '아이템 1의 내용입니다.', true, true, false, '2022-12-01 00:00:00');
insert into content_item(content_id, service_start_time, service_end_time, title, body, live, preview, del, register_time) values ('TEST_CONTENT_02', '2022-12-02 00:00:00', '2023-12-30 00:00:00', '아이템 2', '아이템 2의 내용입니다.', true, true, false, '2022-12-02 00:00:00');
insert into content_item(content_id, service_start_time, service_end_time, title, body, live, preview, del, register_time) values ('TEST_CONTENT_02', '2022-12-03 00:00:00', '2023-05-03 00:00:00', '아이템 3', '아이템 3의 내용입니다.', true, true, false, '2022-12-03 00:00:00');
insert into content(content_id, content_type, title, url, del, register_time) values ('JOIN_AGREE_1', 1, '회원가입 > 개인정보 수집 이용 동의(필수)', null, false, '2022-12-01 00:00:00');
insert into content_item(content_id, service_start_time, service_end_time, title, body, live, preview, del, register_time) values ('JOIN_AGREE_1', '2022-12-01 00:00:00', '2023-12-30 00:00:00', '동의 내용', '개인정보 수집에 동의하셔야 회원가입이 가능합니다.', true, true, false, '2022-12-01 00:00:00');
insert into content(content_id, content_type, title, url, del, register_time) values ('JOIN_AGREE_2', 1, '회원가입 > 개인정보 제 3자 제공 동의(필수)', null, false, '2022-12-01 00:00:00');
insert into content_item(content_id, service_start_time, service_end_time, title, body, live, preview, del, register_time) values ('JOIN_AGREE_2', '2022-12-01 00:00:00', '2023-12-30 00:00:00', '동의 내용', '개인정보 제 3자 제공에 동의하셔야 회원가입이 가능합니다.', true, true, false, '2022-12-01 00:00:00');


insert into content(content_id, content_type, title, url, del, register_time) values ('MAIN_CONTENT', 1, '메인 > 콘텐츠', null, false, '2022-12-01 00:00:00');
insert into content_item(content_id, service_start_time, service_end_time, title, body, live, preview, del, register_time) values ('MAIN_CONTENT', '2022-12-01 00:00:00', '2023-12-30 00:00:00', '메인 콘텐츠', '', true, true, false, '2022-12-01 00:00:00');

insert into content(content_id, content_type, title, url, del, register_time) values ('STORY_MAIN_CONTENT', 1, '메인 > STORY', null, false, '2022-12-01 00:00:00');
insert into content_item(content_id, service_start_time, service_end_time, title, body, live, preview, del, register_time) values ('STORY_MAIN_CONTENT', '2022-12-01 00:00:00', '2023-12-30 00:00:00', 'STORY 콘텐츠', 'STORY 콘텐츠', true, true, false, '2022-12-01 00:00:00');

insert into content(content_id, content_type, title, url, del, register_time) values ('SHOP_MAIN_CONTENT', 1, '메인 > SHOP', null, false, '2022-12-01 00:00:00');
insert into content_item(content_id, service_start_time, service_end_time, title, body, live, preview, del, register_time) values ('SHOP_MAIN_CONTENT', '2022-12-01 00:00:00', '2023-12-30 00:00:00', 'SHOP 콘텐츠', 'SHOP 콘텐츠', true, true, false, '2022-12-01 00:00:00');

insert into content(content_id, content_type, title, url, del, register_time) values ('EVENT_MAIN_CONTENT', 1, '메인 > EVENT', null, false, '2022-12-01 00:00:00');
insert into content_item(content_id, service_start_time, service_end_time, title, body, live, preview, del, register_time) values ('EVENT_MAIN_CONTENT', '2022-12-01 00:00:00', '2023-12-30 00:00:00', 'EVENT 콘텐츠', 'EVENT 콘텐츠', true, true, false, '2022-12-01 00:00:00');

insert into event(title, description, del, register_time) values ('이벤트 신청1', '기본 이벤트', false, '2022-12-22 00:00:00');

insert into board(board_type, title, description, del, register_time) values (1, '공지사항', '공지사항 게시판입니다.', false, '2022-12-22 00:00:00');
insert into board_content(board_id, title, body, visible, del, register_time) values (1, '플렉스가 준비되고 있어요1', '반가워요~', true, false, '2022-12-22 00:00:00');
insert into board_content(board_id, title, body, visible, del, register_time) values (1, '플렉스가 준비되고 있어요2', '반가워요~', true, false, '2022-12-22 00:00:00');
insert into board_content(board_id, title, body, visible, del, register_time) values (1, '플렉스가 준비되고 있어요3', '반가워요~', true, false, '2022-12-22 00:00:00');
insert into board_content(board_id, title, body, visible, del, register_time) values (1, '플렉스가 준비되고 있어요4', '반가워요~', true, false, '2022-12-22 00:00:00');
insert into board_content(board_id, title, body, visible, del, register_time) values (1, '플렉스가 준비되고 있어요5', '반가워요~', true, false, '2022-12-22 00:00:00');
insert into board_content(board_id, title, body, visible, del, register_time) values (1, '플렉스가 준비되고 있어요6', '반가워요~', true, false, '2022-12-22 00:00:00');
insert into board_content(board_id, title, body, visible, del, register_time) values (1, '플렉스가 준비되고 있어요7', '반가워요~', true, false, '2022-12-22 00:00:00');
insert into board_content(board_id, title, body, visible, del, register_time) values (1, '플렉스가 준비되고 있어요8', '반가워요~', true, false, '2022-12-22 00:00:00');
insert into board_content(board_id, title, body, visible, del, register_time) values (1, '플렉스가 준비되고 있어요9', '반가워요~', true, false, '2022-12-22 00:00:00');
insert into board_content(board_id, title, body, visible, del, register_time) values (1, '플렉스가 준비되고 있어요10', '반가워요~', true, false, '2022-12-22 00:00:00');
insert into board_content(board_id, title, body, visible, del, register_time) values (1, '플렉스가 준비되고 있어요12', '반가워요~', true, false, '2022-12-22 00:00:00');
insert into board_content(board_id, title, body, visible, del, register_time) values (1, '플렉스가 준비되고 있어요13', '반가워요~', true, false, '2022-12-22 00:00:00');
insert into board_content(board_id, title, body, visible, del, register_time) values (1, '플렉스가 준비되고 있어요14', '반가워요~', true, false, '2022-12-22 00:00:00');
insert into board_content(board_id, title, body, visible, del, register_time) values (1, '플렉스가 준비되고 있어요15', '반가워요~', true, false, '2022-12-22 00:00:00');
insert into board_content(board_id, title, body, visible, del, register_time) values (1, '플렉스가 준비되고 있어요16', '반가워요~', true, false, '2022-12-22 00:00:00');
insert into board_content(board_id, title, body, visible, del, register_time) values (1, '플렉스가 준비되고 있어요17', '반가워요~', true, false, '2022-12-22 00:00:00');
insert into board_content(board_id, title, body, visible, del, register_time) values (1, '플렉스가 준비되고 있어요18', '반가워요~', true, false, '2022-12-22 00:00:00');
insert into board_content(board_id, title, body, visible, del, register_time) values (1, '플렉스가 준비되고 있어요19', '반가워요~', true, false, '2022-12-22 00:00:00');
insert into board_content(board_id, title, body, visible, del, register_time) values (1, '플렉스가 준비되고 있어요20', '반가워요~', true, false, '2022-12-22 00:00:00');
insert into board_content(board_id, title, body, visible, del, register_time) values (1, '플렉스가 준비되고 있어요21', '반가워요~', true, false, '2022-12-22 00:00:00');
insert into board_content(board_id, title, body, visible, del, register_time) values (1, '플렉스가 준비되고 있어요22', '반가워요~', true, false, '2022-12-22 00:00:00');
insert into board_content(board_id, title, body, visible, del, register_time) values (1, '플렉스가 준비되고 있어요23', '반가워요~', true, false, '2022-12-22 00:00:00');
















