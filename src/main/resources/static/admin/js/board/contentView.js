var app = new Vue({
	el: '#app',
	data: {
		boardId: new URLSearchParams(window.location.search).get('boardId'),
		contentId: new URLSearchParams(window.location.search).get('contentId'),
		content: {
			contentId: 0,
			title: '',
			body: ''
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
				url: '/admin/api/v1/boards/' + me.boardId + '/contents/' + me.contentId,
				params: {}
			})
			.then(function(response) {
				me.content = response.data;

				if(me.content.boardId == 0 || me.content.contentId == 0) {
					me.fnCancel();
				}
			})
			.catch(function(error) {
				modalView.openAlert(error);
			})
		},

		fnDelete() {
			let me = this;
			modalView.openConfirm(
				'삭제 하시겠습니까?',
				function(){
					axios.delete('/admin/api/v1/boards/' + me.boardId + '/contents/' + me.contentId)
					.then(function(response) {
						if(response.data.success == true){
							modalView.openAlert(
								'삭제 되었습니다.'
								,function() {
									me.fnCancel();
								}
							);
						} else {
							modalView.openAlert(response.data.message);
						}
					})
					.catch(function(error) {
						modalView.openAlert(error.message);
					})
				}
			);
		},

		// 노출여부 변경처리.
		fnEditVisible(visible) {
			let me = this;
			modalView.openConfirm(
				'노출상태를 변경하시겠습니까?',
				function() {
					axios.put('/admin/api/v1/boards/' + me.boardId + '/contents/' + me.contentId + '/' + visible)
					.then(function(response) {
						if(response.data.success == true){
							modalView.openAlert(
								'노출상태를 변경하였습니다.'
								,function() {
									var currUrl = document.location.href;
									document.location.href = currUrl;
								}
							);
						} else {
							modalView.openAlert(response.data.message);
						}
					})
					.catch(function(error) {
						modalView.openAlert(error.message);
					})
				}
			);
		},

		fnCancel() {
			let me = this;
			document.location.href = '/admin/board/boards/view?boardId=' + me.boardId;
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