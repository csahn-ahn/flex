var app = new Vue({
	el: '#app',
	data: {
		contentId: new URLSearchParams(window.location.search).get('contentId'),
		content: {},
		items: [],
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

			if(me.contentId) {
				axios({
					method:'get',
					url: '/admin/api/v1/contents/' + me.contentId,
					params: {}
				})
				.then(function(response) {
					me.content = response.data;
					if(me.content == null || me.content.contentId == null) {
						me.fnCancel();
					}
					me.fnGetItemList();
				})
				.catch(function(error) {
					modalView.openAlert(error);
				})
			}
		},

		// 목록 조회
		fnGetItemList() {
			let me = this;
			axios({
				method:'get',
				url: '/admin/api/v1/contents/' + me.contentId + '/items',
				params: {}
			})
			.then(function(response) {
				me.items = response.data;

				var isExistLiveService = false;
				var isExistPreviewService = false;

				for(var i=0; i<me.items.length; i++) {
					var item = me.items[i];
					if(!isExistLiveService && item.serviceStatus && item.live) {
						item.liveService = true;
						isExistLiveService = true;
					}
					if(!isExistPreviewService && item.serviceStatus && item.preview) {
						item.previewService = true;
						isExistPreviewService = true;
					}
				}
			})
			.catch(function(error) {
				modalView.openAlert(error.message);
			})
		},

		fnOpenItemPopup(obj) {
			let me = this;
			var itemId = obj ? obj.itemId : '';
			var url = '/admin/content/contents/itemRegister?contentId=' + me.contentId + '&itemId=' + itemId;
			var name = "itemRegister";
			var option = "width = 1600, height = 900, top = 00, left = 0, location = no"
			var popup = window.open(url, name, option);
		},

		// LIVE 설정
		fnSetLive(item) {
			let me = this;
			var status = item.live == true ? false : true;
			var title = item.live == true ? '적용 해제' : '적용';

			modalView.openConfirm(
				title + ' 하시겠습니까?',
				function(){
					axios.put(
						'/admin/api/v1/contents/' + me.contentId + '/items/' + item.itemId + '/live/' + status,
						{}
					)
					.then(function(response) {
						modalView.openAlert(
							title + '되었습니다.'
							,function() {
								me.fnGetItemList();
							}
						);
					})
					.catch(function(error) {
						modalView.openAlert(error);
					})
				},
				function() {
				}
			);
		},

		// PREVIEW 설정
		fnSetPreview(item) {
			let me = this;
			var status = item.preview == true ? false : true;
			var title = item.preview == true ? '적용 해제' : '적용';

			modalView.openConfirm(
				title + ' 하시겠습니까?',
				function(){
					axios.put(
						'/admin/api/v1/contents/' + me.contentId + '/items/' + item.itemId + '/preview/' + status,
						{}
					)
					.then(function(response) {
						modalView.openAlert(
							title + '되었습니다.'
							,function() {
								me.fnGetItemList();
							}
						);
					})
					.catch(function(error) {
						modalView.openAlert(error);
					})
				},
				function() {
				}
			);
		},

		fnCopy(obj) {
			let me = this;
			modalView.openConfirm(
				'복사 하시겠습니까?',
				function(){
					axios.post('/admin/api/v1/contents/' + me.contentId + '/items/' + obj.itemId + '/copy')
					.then(function(response) {
						if(response.data.success == true){
							modalView.openAlert(
								'복사 되었습니다.'
								,function() {
									me.fnGetItemList();
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

		fnDelete(obj) {
			let me = this;
			modalView.openConfirm(
				'삭제 하시겠습니까?',
				function(){
					axios.delete('/admin/api/v1/contents/' + me.contentId + '/items/' + obj.itemId)
					.then(function(response) {
						if(response.data.success == true){
							modalView.openAlert(
								'삭제 되었습니다.'
								,function() {
									me.fnGetItemList();
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
			document.location.href = '/admin/content/contents/main';
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