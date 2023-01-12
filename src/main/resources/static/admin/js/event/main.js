var app = new Vue({
	el: '#app',
	data: {
		totalCount: 0,
		list: [],
		search: {
			page: 1,
			pageSize: 10,
			eventId: 0,
			title: '',
		},
	},
	created() {
	},
	mounted() {
		let me = this;
		me.fnSearch();
	},
	methods: {

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

		// 목록 조회
		fnGetList() {
			let me = this;
			axios({
				method:'get',
				url: '/admin/api/v1/events',
				params: me.search
			})
			.then(function(response) {
				me.totalCount = response.data.totalElements;
				me.list = response.data.content;

				totalPages = response.data.totalPages;
				totalPages = totalPages == 0 ? 1 : totalPages;
				visiblePages = response.data.size;
				visiblePages = visiblePages == 0 ? 1 : visiblePages;

				$('#pagination').twbsPagination({
					totalPages: totalPages,
					visiblePages: visiblePages,
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
			document.location.href = '/admin/event/events/view?eventId=' + obj.eventId;
		},

		fnUpdate(obj) {
			document.location.href = '/admin/event/events/register?eventId=' + obj.eventId;
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
									document.location.href = '/admin/event/events/main';
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