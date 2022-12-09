var app = new Vue({
	el: '#app',
	data: {
		menuId: new URLSearchParams(window.location.search).get('menuId'),
		upperMenuId: new URLSearchParams(window.location.search).get('upperMenuId'),
		upperMenus: [],
		menu: {
			upperMenuId: 0,
			linkType: 1,
			linkUrl: '',
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

			me.upperMenuId = me.upperMenuId == null || me.upperMenuId == '' ? 0 : me.upperMenuId;

			// 하위메뉴 신규 등록시 상위메뉴 자동 설정.
			if(!me.menuId && me.upperMenuId > 0) {
				me.menu.upperMenuId = me.upperMenuId;
			}

			me.fnGetUpperMenus();

			if(me.menuId) {
				me.fnGetMenu();
			}
		},

		fnGetUpperMenus() {
			let me = this;
			axios({
				method:'get',
				url: '/admin/api/v1/adminMenus/upper',
				params: {}
			})
			.then(function(response) {
				me.upperMenus = response.data;

				if(me.upperMenuId > 0) {
					var upperMenu = me.upperMenus.filter(obj => obj.menuId == me.upperMenuId)[0];
					me.menu.linkUrl = upperMenu.linkUrl + '/';
				}
			})
			.catch(function(error) {
				modalView.openAlert(error + '1');
			})
		},

		fnGetMenu() {
			let me = this;
			axios({
				method:'get',
				url: '/admin/api/v1/adminMenus/' + me.menuId,
				params: {}
			})
			.then(function(response) {
				me.menu = response.data;

				if(me.menu.menuId == null) {
					me.fnCancel();
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
					axios.post(
						'/admin/api/v1/adminMenus',
						me.menu
					)
					.then(function(response) {
						modalView.openAlert(
							'저장 되었습니다.'
							,function() {
								document.location.href = '/admin/system/adminMenus/main';
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
			return false;
		},

		fnCancel() {
			document.location.href = '/admin/system/adminMenus/main';
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