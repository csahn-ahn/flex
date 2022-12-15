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
				console.log(response);
			})
			.catch(function(error) {
				console.log(error)
			})
		},
	},
	computed: {
	}
});
