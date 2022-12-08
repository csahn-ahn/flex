var app = new Vue({
	el: '#app',
	data: {
		managers: [],
		search: {
			page: 1,
			pageSize: 10,
			groupId: 0,
			username: '',
			name: ''
		}
	},
	created() {
	},
	mounted() {
		let me = this;
		me.fnGetManagers();
	},
	methods: {

		fnSearch() {
			let me = this;
			me.page = 1;
			me.fnGetManagers();
		},

		fnGetManagers() {
			let me = this;
			axios({
				method:'get',
				url: '/admin/api/v1/managers',
				params: me.search
			})
			.then(function(response) {
				me.managers = response.data;

                 $('#pagination').twbsPagination({
					totalPages: 35,
					visiblePages: 10,
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
						modalView.openAlert(
							'삭제 되었습니다.'
							,function() {
								document.location.href = '/admin/system/managers/main';
							}
						);
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
		}
	},
	computed: {
	}
});