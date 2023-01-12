var app = new Vue({
	el: '#app',
	data: {
		boardId: new URLSearchParams(window.location.search).get('boardId'),
		board: {
			boardId: 0,
			boardType: 1,
			title: '',
			description: ''
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

			if(me.boardId != '' && me.boardId != null) {
				axios({
					method:'get',
					url: '/admin/api/v1/boards/' + me.boardId,
					params: {}
				})
				.then(function(response) {
					me.board = response.data;

					if(me.boardId == 0 || me.board.boardId == 0) {
						me.fnCancel();
					}
				})
				.catch(function(error) {
					modalView.openAlert(error);
				})
			}
		},

		fnSubmit() {
			let me = this;
			let title = me.eventId > 0 ? '수정' : '등록';

			modalView.openConfirm(
				title + ' 하시겠습니까?',
				function(){
					axios.post(
						'/admin/api/v1/boards',
						me.board
					)
					.then(function(response) {
						if(response.data.success){
							modalView.openAlert(
								title + ' 되었습니다.'
								,function() {
									document.location.href = '/admin/board/boards/main';
								}
							);
						}else{
							modalView.openAlert(response.data.message);
						}
					})
					.catch(function(error) {
						modalView.openAlert(error);
					})
				},
				function() {
				}
			);
			return false;
		},

		fnCancel() {
			document.location.href = '/admin/board/boards/main';
		},
	},
	computed: {
	}
});

$(function() {
	$("form").on("submit", function(){
		app.fnSubmit();
		return false;
	})
})