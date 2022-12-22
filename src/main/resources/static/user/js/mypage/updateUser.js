var app = new Vue({
	el: '#app',
	data: {
		user: {}
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
				alert(error);
			})
		},

		fnSave() {
			let me = this;
			axios.post(
				'/user/api/v1/users/updateUser',
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