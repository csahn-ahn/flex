var app = new Vue({
	el: '#app',
	data: {
		user: {
			username: 'user01',
			password: '1234',
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
		fnLogin() {
			let me = this;
			axios.post(
				'/user/api/v1/users/login',
				me.user
			)
			.then(function(response) {
				if(response.data.success) {
					if(response.data.data == null) {
						var returnUrl = sessionStorage.getItem('returnUrl');
						if(returnUrl != null && returnUrl != '') {
							sessionStorage.setItem('returnUrl', null);
							document.location.href = returnUrl;
						}else{
							document.location.href = '/';
						}

					}else{
						if(response.data.data.isTempPassword == true) {
							alert('비밀번호 변경필요. (임시 비밀번호)');
						}
					}
				}else{
					alert('로그인실패 : ' + response.data.message);
				}
			})
			.catch(function(error) {
				console.log('error : ' + error);
			})

			return false;
		},
	}
});

$(function() {
	$("form").on("submit", function(){
		app.fnLogin();
		return false;
	})
})