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
				console.log(response);
			})
			.catch(function(error) {
				console.log(error)
			})
		},

		// 일자 포맷 변경.
		convertDateFormat(date) {
			return convertDateFormat(date, 'YYYY.MM.DD HH:mm');
		}
	},
	computed: {
	}
});
