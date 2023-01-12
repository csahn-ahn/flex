var app = new Vue({
	el: '#app',
	data: {
		boardId: 1,
		contentId: new URLSearchParams(window.location.search).get('contentId'),
		page:  new URLSearchParams(window.location.search).get('page'),
		content: {
			contentId: 0,
			title: '',
			body: ''
		},
	},
	created() {
	},
	mounted() {
		let me = this;
		me.init();
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
				url: '/user/api/v1/boards/' + me.boardId + '/contents/' + me.contentId,
				params: {}
			})
			.then(function(response) {
				me.content = response.data;

				if(me.content.boardId == 0 || me.content.contentId == 0) {
					me.fnList();
				}
			})
			.catch(function(error) {
				_popup.fnOpenAlert(error);
			})
		},

		fnList() {
			let me = this;
			document.location.href = '/user/notice/list?page=' + me.page;
		},
	},
	computed: {
	}
});