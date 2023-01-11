var app = new Vue({
	el: '#app',
	data: {
		user: {

		}
	},
	created() {
	},
	mounted() {
		let me = this;
		me.fnGetUser();
	},
	computed: {
	},
	methods: {

		fnGetUser() {
			let me = this;
			axios({
				method:'get',
				url: '/user/api/v1/users/me',
				params: {}
			})
			.then(function(response) {
				me.user = response.data;
			})
			.catch(function(error) {
				_popup.fnOpenAlert(error);
			})
		},

		fnValidate() {
			let me = this;
			if(me.user.password == null || me.user.password == '') {
				_popup.fnOpenAlert('비밀번호를 입력하십시오.');
				return false;
			}
			return true;
		},

		fnSave() {
			let me = this;

			if(!me.fnValidate()) {
				return false;
			}

			axios.put(
				'/user/api/v1/users/updateUser',
				me.user
			)
			.then(function(response) {
				if(response.data.success) {
					_popup.fnOpenAlert('개인정보가 수정되었습니다.', function() {
						document.location.href = '/';
					})
				}else{
					_popup.fnOpenAlert(response.data.message);
				}
			})
			.catch(function(error) {
				_popup.fnOpenAlert('error : ' + error);
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