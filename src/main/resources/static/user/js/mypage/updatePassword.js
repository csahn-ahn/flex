var app = new Vue({
	el: '#app',
	data: {
		user: {
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
	computed: {
	},
	methods: {
		fnSave() {
			let me = this;
			if(me.user.password == '' || me.user.newPassword == '' || me.user.confirmPassword == '') {
				alert('비밀번호를 입력해주세요.');
				return false;
			}

			if(me.user.newPassword !== me.user.confirmPassword) {
				alert('새로운 비밀번호가 일치하지 않습니다.');
				return false;
			}

			axios.put(
				'/user/api/v1/users/password',
				me.user
			)
			.then(function(response) {
				if(response.data.success) {
					alert('비밀번호가 변경되었습니다.\n변경 된 비밀번호로 다시 로그인하여 주십시오.');
					_common.fnLogin('/');
				}else{
					me.user= {
						password: '',
						newPassword: '',
						confirmPassword: ''
					}
					alert(response.data.message);
				}
			})
			.catch(function(error) {
				alert('error : ' + error);
			})

			return false;
		},

		fnCancel() {
			document.location.href = '/';
		},
	}
});

$(function() {
	$("form").on("submit", function(){
		app.fnSave();
		return false;
	})
})