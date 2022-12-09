var app = new Vue({
	el: '#app',
	data: {
		groupId: new URLSearchParams(window.location.search).get('groupId'),
		menus: [],

		// 전체 선택.
		checkAllCreate: false,
		checkAllRead: false,
		checkAllUpdate: false,
		checkAllDelete: false,
		checkAllDownload: false,

		// 설정된 메뉴 권한
		menuGroups: [],


	},
	created() {
	},
	mounted() {
		let me = this;
		me.fnGetMenus();
	},
	watch: {
		checkAllRead() {
			let me = this;
			for(var i=0; i<me.menus.length; i++) {
				me.menus[i].hasRead = me.checkAllRead;
			}
		},
		checkAllCreate() {
			let me = this;
			for(var i=0; i<me.menus.length; i++) {
				me.menus[i].hasCreate = me.checkAllCreate;
			}
		},
		checkAllUpdate() {
			let me = this;
			for(var i=0; i<me.menus.length; i++) {
				me.menus[i].hasUpdate = me.checkAllUpdate;
			}
		},
		checkAllDelete() {
			let me = this;
			for(var i=0; i<me.menus.length; i++) {
				me.menus[i].hasDelete = me.checkAllDelete;
			}
		},
		checkAllDownload() {
			let me = this;
			for(var i=0; i<me.menus.length; i++) {
				me.menus[i].hasDownload = me.checkAllDownload;
			}
		},


	},
	methods: {
		init() {
			let me = this;
		},

		fnGetMenus() {
			let me = this;

			axios({
				method:'get',
				url: '/admin/api/v1/adminGroupMenus',
				params: {groupId: me.groupId}
			})
			.then(function(response) {
				me.menus = response.data;
				for(var i=0; i<me.menus.length; i++) {
					me.menus[i].groupId = me.groupId;
				}
			})
			.catch(function(error) {
				modalView.openAlert(error);
			})
		},

		fnSubmit() {
			let me = this;
			modalView.openConfirm(
				'저장 하시겠습니까?',
				function(){
					axios.post('/admin/api/v1/adminGroupMenus',
						{
							groupId: me.groupId,
							adminGroupMenuList: me.menus
						}
					)
					.then(function(response) {
						if(response.data.success) {
							modalView.openAlert(
								'저장 되었습니다.'
								,function() {
									document.location.href = '/admin/system/adminGroups/main';
								}
							);
						}else{
							modalView.openAlert('저장을 실패하였습니다.\n' + response.data.message);
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
			document.location.href = '/admin/system/adminGroups/main';
		},
	},
	computed: {
	}
});