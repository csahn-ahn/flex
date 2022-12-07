var app = new Vue({
	el: '#app',
	data: {
		managers: [],
	},
	created() {
	},
	mounted() {
		let me = this;
		me.fnGetManagers();
	},
	methods: {
		fnGetManagers() {
			console.log('fnGetManagers : ');
			let me = this;

			axios.get('/admin/api/v1/managers')
			.then(function(response) {
				console.log(response);
				me.managers = response.data;
			})
			.catch(function(error) {
				console.log(error)
			})

			/*
			me.managers = [
				{username: 'admin', name: '슈퍼관리자', groupName: '슈퍼관리자 그룹', registerDate: '2022-01-01 23:59:59', active: true},
				{username: 'manager01', name: '운영자01', groupName: '운영자 그룹', registerDate: '2022-11-11 12:59:59', active: true},
				{username: 'manager02', name: '운영자02', groupName: '운영자 그룹', registerDate: '2022-11-11 12:59:59', active: true},
				{username: 'manager03', name: '운영자03', groupName: '운영자 그룹', registerDate: '2022-11-11 12:59:59', active: true},
				{username: 'manager04', name: '운영자04', groupName: '운영자 그룹', registerDate: '2022-11-11 12:59:59', active: true},
				{username: 'manager05', name: '운영자05', groupName: '운영자 그룹', registerDate: '2022-11-11 12:59:59', active: true},
				{username: 'manager06', name: '운영자06', groupName: '운영자 그룹', registerDate: '2022-11-11 12:59:59', active: true},
				{username: 'manager07', name: '운영자07', groupName: '운영자 그룹', registerDate: '2022-11-11 12:59:59', active: true},
				{username: 'manager08', name: '운영자08', groupName: '운영자 그룹', registerDate: '2022-11-11 12:59:59', active: true},
				{username: 'manager09', name: '운영자09', groupName: '운영자 그룹', registerDate: '2022-11-11 12:59:59', active: true},
				{username: 'manager10', name: '운영자10', groupName: '운영자 그룹', registerDate: '2022-11-11 12:59:59', active: true},
			]
			*/
		},

		// 일자 포맷 변경.
		convertDateFormat(date) {
			return convertDateFormat(date, 'YYYY.MM.DD HH:mm');
		}
	},
	computed: {
	}
});
