var app = new Vue({
	el: '#app',
	data: {
		boardId: new URLSearchParams(window.location.search).get('boardId'),
		board: {
			boardId: 0,
			title: '',
			description: ''
		},
		totalCount: 0,
		list: [],
		search: {
			page: 1,
			pageSize: 10,
			username: '',
			name: '',
			title: '',
		},
	},
	created() {
	},
	mounted() {
		let me = this;
		me.init();
		me.fnSearch();
	},
	watch: {
	},
	methods: {
		init() {
			let me = this;

			if(me.boardId == null || me.boardId == 0) {
				return;
			}
			axios({
				method:'get',
				url: '/admin/api/v1/boards/' + me.boardId,
				params: {}
			})
			.then(function(response) {
				me.board = response.data;

				if(me.board.boardId == 0) {
					me.fnCancel();
				}
			})
			.catch(function(error) {
				modalView.openAlert(error);
			})
		},

		// 검색
		fnSearch() {
			let me = this;
			me.page = 1;
			me.fnGetList();
		},

		fnSetPage(page) {
			let me = this;
			me.search.page = page;
			me.fnGetList();
		},

		// 신청 목록 조회
		fnGetList() {
			let me = this;
			axios({
				method:'get',
				url: '/admin/api/v1/boards/' + me.boardId + '/contents',
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

		fnDelete(obj) {
			let me = this;
			modalView.openConfirm(
				'삭제 하시겠습니까?',
				function(){
					axios.delete('/admin/api/v1/boards/' + me.boardId + '/' + obj.contentId)
					.then(function(response) {
						if(response.data.success == true){
							modalView.openAlert(
								'삭제 되었습니다.'
								,function() {
									me.fnSetPage(1);
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

		fnCancel() {
			document.location.href = '/admin/board/boards/main';
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