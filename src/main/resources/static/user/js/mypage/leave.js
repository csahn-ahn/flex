var app = new Vue({
	el: '#app',
	data: {
		user: {
			password: '',
			temp: ''
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
			if(me.user.password == '') {
				alert('비밀번호를 입력해주세요.');
				return false;
			}

			axios.post(
				'/user/api/v1/users/leave',
				me.user
			)
			.then(function(response) {
				if(response.data.success) {
					alert('탈퇴되었습니다.');
					document.location.href = '/';
				}else{
					me.user.password = '';
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