var app = new Vue({
	el: '#app',
	data: {
		groupId: new URLSearchParams(window.location.search).get('groupId'),
		group: {
			groupId: 0,
			groupName: '',
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
			if(me.groupId) {
				me.fnGetGroup();
			}
		},

		fnGetGroup() {
			let me = this;
			axios({
				method:'get',
				url: '/admin/api/v1/adminGroups/' + me.groupId,
				params: {}
			})
			.then(function(response) {
				me.group = response.data;

				if(me.group.groupId == 0) {
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
						'/admin/api/v1/adminGroups',
						me.group
					)
					.then(function(response) {
						modalView.openAlert(
							'저장 되었습니다.'
							,function() {
								document.location.href = '/admin/system/adminGroups/main';
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
			document.location.href = '/admin/system/adminGroups/main';
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