var app = new Vue({
	el: '#app',
	data: {
		user: {
			username: 'csahn@univ.me',
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
							_popup.fnOpenAlert('비밀번호 변경필요. (임시 비밀번호)');
						}
					}
				}else{
					_popup.fnOpenAlert('로그인실패 : ' + response.data.message);
				}
			})
			.catch(function(error) {
				_popup.fnOpenAlert('error : ' + error);
			})

			return false;
		},

		fnSocialSuccess(snsType, snsUid) {
			let me = this;
			axios.post(
				'/user/api/v1/users/loginSns',
				{
					'snsType': snsType,
					'snsUid': snsUid
				}
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
							_popup.fnOpenAlert('비밀번호 변경필요. (임시 비밀번호)');
						}
					}
				}else{
					_popup.fnOpenAlert('일치하는 회원정보가 존재하지 않습니다.');
				}
			})
			.catch(function(error) {
				_popup.fnOpenAlert('오류가 발생하였습니다. - ' + error.message);
			})

			return false;
		},

		fnOpenSocialLogin(channel) {
			var url = '/user/' + channel + '/login';
			var name = 'sociallogin';
			var spec = '';

			switch (channel) {
			  case 'naver':
				spec = 'width=445,height=510,scrollbars=yes';
				break;
			  case 'facebook':
				spec = 'width=620,height=600';
				break;
			  case 'google':
				spec = 'width=620,height=600';
				break;
			  case 'kakao':
				spec = 'width=620,height=600';
				break;
			  default:
				break;
			}

			if(channel != 'kakao') {
				_popup.fnOpenAlert('준비중입니다.');
				return false;
			}else{
				socialLoginWindow = window.open(url, name, spec);
			}
		},

		fnCancel() {
			document.location.href = '/';
		},
	}
});

$(function() {
	$("form").on("submit", function(){
		app.fnLogin();
		return false;
	})
})