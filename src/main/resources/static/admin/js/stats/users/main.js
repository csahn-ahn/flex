var app = new Vue({
	el: '#app',
	data: {
		list: [],
	},
	created() {
	},
	mounted() {
		let me = this;
	},
	methods: {
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
