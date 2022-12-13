var app = new Vue({
	el: '#app',
	data: {
		list: [],
	},
	created() {
	},
	mounted() {
		let me = this;
		me.fnGetMenus();
	},
	methods: {
		fnGetMenus() {
			let me = this;

			axios.get('/admin/api/v1/adminMenus')
			.then(function(response) {
				me.list = response.data;
			})
			.catch(function(error) {
				console.log(error)
			})
		},

		// 일자 포맷 변경.
		convertDateFormat(date) {
			return convertDateFormat(date, 'YYYY.MM.DD HH:mm');
		},

		// 메뉴 수정.
		fnUpdate(obj) {
			document.location.href = '/admin/system/adminMenus/register?menuId=' + obj.menuId;
		},

		// 하위메뉴 등록
		fnMakeLowerMenu(obj) {
			document.location.href = '/admin/system/adminMenus/register?upperMenuId=' + obj.menuId;
		},

		// 메뉴 삭제
		fnDelete(obj) {
			modalView.openConfirm(
				'삭제 하시겠습니까?',
				function(){
					axios.delete('/admin/api/v1/adminMenus/' + obj.menuId)
					.then(function(response) {
						if(response.data.success) {
							modalView.openAlert(
								'삭제 되었습니다.'
								,function() {
									document.location.href = document.location.href;
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
