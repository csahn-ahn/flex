var app = new Vue({
	el: '#app',
	data: {
		 list: [],
	},
	created() {
	},
	mounted() {
		let me = this;
		me.fnGetGroups();
	},
	methods: {
		fnGetGroups() {
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
		},

		// 메뉴 수정.
		fnUpdate(obj) {
			document.location.href = '/admin/system/adminGroups/register?groupId=' + obj.groupId;
		},

		// 그룹 삭제
		fnDelete(obj) {
			modalView.openConfirm(
				'삭제 하시겠습니까?',
				function(){
					axios.delete('/admin/api/v1/adminGroups/' + obj.groupId)
					.then(function(response) {
						if(response.data.success == true){
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
	},
	computed: {
	}
});
