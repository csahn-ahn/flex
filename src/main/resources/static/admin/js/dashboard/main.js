var app = new Vue({
	el: '#app',
	data: {
		todayUserStats: {
			totalCount: 10,
			todayNewCount: 20,
			todayLoginCount: 30,
			todayDeleteCount: 40,
		}
	},
	created() {
	},
	mounted() {
		let me = this;
		me.fnGetTodayUserStatus();
	},
	methods: {

		// 사용자 집계 조회
		fnGetTodayUserStatus() {
			let me = this;
			axios({
				method:'get',
				url: '/admin/api/v1/dashboard/todayUserStats',
				params: {}
			})
			.then(function(response) {
				me.todayUserStats = response.data;
			})
			.catch(function(error) {
				modalView.openAlert(error.message);
			})
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