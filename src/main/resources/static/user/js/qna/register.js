var app = new Vue({
	el: '#app',
	data: {
		boardId: 2,
		qnaTypeGroupCode: 'QNA_TYPE',
		qnaTypeCodes: [],
		obj: {
			boardId: 2,
			contentId: 0,
			codeId: '',
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

		fnSave() {
			let me = this;
			modalView.openConfirm(
				'문의를 등록 하시겠습니까?',
				function(){
					axios.post(
						'/user/api/v1/boards/' + boardId + '/contents' ,
						me.obj
					)
					.then(function(response) {
						if(response.data.success){
							modalView.openAlert(
								'등록 되었습니다.'
								,function() {
									document.location.href = '/user/qna';
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
			document.location.href = '/';
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