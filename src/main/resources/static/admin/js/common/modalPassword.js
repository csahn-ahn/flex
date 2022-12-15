var passwordModalView = new Vue({
	el: '#modalPasswordWindow',
	data: {
		updateData: {
			password: '',
			newPassword: '',
			confirmPassword: ''
		}
	},
	created() {
	},
	mounted() {
		let me = this;
	},
	methods: {

		open() {
			let me = this;
			me.updateData = {
				password: '',
				newPassword: '',
				confirmPassword: ''
			}
			$("button[id=btnPasswordModal]").click();
		},

		fnSubmit() {
			let me = this;

			if(!me.validate()) {
				return false;
			}

			axios.put(
				'/admin/api/v1/managers/password',
				me.updateData
			)
			.then(function(response) {
				if(response.data.success == true){
					me.fnCancel();
					modalView.openAlert(
						'비밀번호가 변경 되었습니다.<br>변경된 비밀번호로 다시 로그인하여 주십시오.'
						,function() {
							document.location.href = '/admin/logout';
						}
					);
				}else{
					me.fnCancel();
					modalView.openAlert(response.data.message);
				}
			})
			.catch(function(error) {
				me.fnCancel();
				modalView.openAlert(error);
			})
		},

		validate() {
			let me = this;
			if (me.updateData.newPassword != me.updateData.confirmPassword) {
				me.fnCancel();
				modalView.openAlert("비밀번호가 일치하지 않습니다.");
				return false;
			}
			return true;
		},

		fnCancel() {
			let me = this;
			me.updateData = {
				password: '',
				newPassword: '',
				confirmPassword: ''
			}
			$("button[id=btnPasswordModal]").click();
		},
	},
	computed: {
	}
});


$(function() {
	$("form").on("submit", function(){
		passwordModalView.fnSubmit();
		return false;
	})
})