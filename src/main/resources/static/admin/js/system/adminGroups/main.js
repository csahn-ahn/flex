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

			axios.get('/admin/api/v1/adminGroups')
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

		// 메뉴설정 이동.
		fnSetupMenu(obj) {
			document.location.href = '/admin/system/adminGroups/setupMenu?groupId=' + obj.groupId;
		}
	},
	computed: {
	}
});
