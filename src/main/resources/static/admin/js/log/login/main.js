var app = new Vue({
	el: '#app',
	data: {
		totalCount: 0,
		list: [],
		search: {
			page: 1,
			pageSize: 10,
			username: '',
			name: ''
		},
	},
	created() {
	},
	mounted() {
		let me = this;
		me.fnGetLogs();
	},
	methods: {
		fnGetLogs() {
			let me = this;
			axios({
				method:'get',
				url: '/admin/api/v1/adminLogLogin',
				params: me.search
			})
			.then(function(response) {
				me.totalCount = response.data.numberOfElements;
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
						me.search.page = page;
					}
				});
			})
			.catch(function(error) {
				modalView.openAlert(error.message);
			})
		},
	},
	computed: {
	}
});
