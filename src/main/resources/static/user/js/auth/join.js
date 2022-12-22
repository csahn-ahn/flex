var app = new Vue({
	el: '#app',
	data: {
		user: {
			username: 'test01',
			name: '테스트-01',
			password: '1234',
			confirmPassword: '1234',
			hp: '01012345678',
			email: 'test01@gmail.com'
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
			axios.post(
				'/user/api/v1/users/join',
				me.user
			)
			.then(function(response) {
				if(response.data.success) {
					document.location.href = '/';
				}else{
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