var app = new Vue({
  el: '#app',
  data: {
	user: null,
	boardId: 1,
	page:  new URLSearchParams(window.location.search).get('page'),
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
  },
  computed: {
  },
  methods: {
  	init() {
  		let me = this;
  		if(me.page != null && me.page != '') {
  			me.page = parseInt(me.page);
  			me.search.page = me.page;
  		}else{
  			me.page = 1;
		}
		me.fnSearch();
  	},

  	// 검색
	fnSearch() {
		let me = this;
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
			url: '/user/api/v1/boards/' + me.boardId + '/contents',
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
				startPage: me.page,
				//first : "첫 페이지",	// 페이지네이션 버튼중 처음으로 돌아가는 버튼에 쓰여 있는 텍스트
				//prev : "이전 페이지",	// 이전 페이지 버튼에 쓰여있는 텍스트
				//next : "다음 페이지",	// 다음 페이지 버튼에 쓰여있는 텍스트
				//last : "마지막 페이지",	// 페이지네이션 버튼중 마지막으로 가는 버튼에 쓰여있는 텍스트
				firstClass : "controller prev-10",	// 첫 페이지 CSS class
				prevClass : "controller prev",	// 이전 페이지 CSS class
				nextClass : "controller next",	// 다음 페이지 CSS class
				lastClass : "controller next-10",	// 마지막 페이지 CSS class
				pageClass : "paging",	// 페이지 버튼의 CSS class
				activeClass : "paging current",	// 클릭된 페이지 버튼의 CSS class
				disabledClass : "",	// 클릭 안된 페이지 버튼의 CSS class
				anchorClass : "",	//버튼 안의 앵커에 대한 CSS class
				onPageClick: function (event, page) {
					me.fnSetPage(page);
				}
			});
		})
		.catch(function(error) {
			_popup.fnOpenAlert(error.message);
		})
	},

	fnView(obj, page) {
		let me = this;
		document.location.href = '/user/notice/view?contentId=' + obj.contentId + '&page=' + page;
	},
  }
});
