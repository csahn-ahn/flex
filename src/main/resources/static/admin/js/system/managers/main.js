var app = new Vue({
	el: '#app',
	data: {
		totalCount: 0,
		list: [],
		search: {
			page: 1,
			pageSize: 10,
			groupId: 0,
			username: '',
			name: ''
		},
		adminGroups: [],
	},
	created() {
	},
	mounted() {
		let me = this;
		me.fnGetAdminGroups();
		me.fnSearch();
	},
	methods: {

		// 검색
		fnSearch() {
			let me = this;
			me.page = 1;
			me.fnGetManagers();
		},

		fnSetPage(page) {
			let me = this;
			me.search.page = page;
			me.fnGetManagers();
		},

		// 검색할 관리자 그룹 조회
		fnGetAdminGroups() {
			let me = this;
			axios({
				method:'get',
				url: '/admin/api/v1/adminGroups',
				params: {}
			})
			.then(function(response) {
				me.adminGroups = response.data;
				if(me.adminGroups.length > 0){
					//me.search.groupId = me.adminGroups[0].groupId;
				}
			})
			.catch(function(error) {
				modalView.openAlert(error.message);
			})
		},

		// 운영자 목록 조회
		fnGetManagers() {
			let me = this;
			axios({
				method:'get',
				url: '/admin/api/v1/managers',
				params: me.search
			})
			.then(function(response) {
				me.totalCount = response.data.totalElements;
				me.list = response.data.content;

                $('#pagination').twbsPagination({
					totalPages: response.data.totalPages,
					visiblePages: response.data.size,
					startPage: 1,
					//first : "첫 페이지",	// 페이지네이션 버튼중 처음으로 돌아가는 버튼에 쓰여 있는 텍스트
					//prev : "이전 페이지",	// 이전 페이지 버튼에 쓰여있는 텍스트
					//next : "다음 페이지",	// 다음 페이지 버튼에 쓰여있는 텍스트
					//last : "마지막 페이지",	// 페이지네이션 버튼중 마지막으로 가는 버튼에 쓰여있는 텍스트
					nextClass : "page-item next",	// 이전 페이지 CSS class
					prevClass : "page-item prev",	// 다음 페이지 CSS class
					lastClass : "page-item last",	// 마지막 페이지 CSS calss
					firstClass : "page-item first",	// 첫 페이지 CSS class
					pageClass : "page-item",	// 페이지 버튼의 CSS class
					activeClass : "active",	// 클릭된 페이지 버튼의 CSS class
					disabledClass : "disabled",	// 클릭 안된 페이지 버튼의 CSS class
					anchorClass : "page-link",	//버튼 안의 앵커에 대한 CSS class
					onPageClick: function (event, page) {
						me.fnSetPage(page);
					}
				});
			})
			.catch(function(error) {
				modalView.openAlert(error.message);
			})
		},

		fnDetail(obj) {
			document.location.href = '/admin/system/managers/' + obj.username;
		},

		fnUpdate(obj) {
			document.location.href = '/admin/system/managers/register?username=' + obj.username;
		},

		fnDelete(obj) {
			modalView.openConfirm(
				'삭제 하시겠습니까?',
				function(){
					axios.delete('/admin/api/v1/managers/' + obj.username)
					.then(function(response) {
						if(response.data.success == true){
							modalView.openAlert(
								'삭제 되었습니다.'
								,function() {
									document.location.href = '/admin/system/managers/main';
								}
							);
						} else {
							modalView.openAlert(response.data.message);
						}
					})
					.catch(function(error) {
						modalView.openAlert(error.message);
					})
				},
				function() {
				}
			);
		},

		// 일자 포맷 변경.
		convertDateFormat(date) {
			return convertDateFormat(date, 'YYYY.MM.DD HH:mm');
		},

		isCreate() {
			return hasCreate == 'true' ? true : false;
		},
		isUpdate() {
			return hasUpdate == 'true' ? true : false;
		},
		isDelete() {
			return hasDelete == 'true' ? true : false;
		},
		isDownload() {
			return hasDownload == 'true' ? true : false;
		},
	},
	computed: {
	}
});

$(function() {
	$("form").on("submit", function(){
		app.fnSearch();
		return false;
	})
})