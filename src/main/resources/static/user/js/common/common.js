var _common = new Vue({
  el: '#common',
  data: {
  },
  created() {
  },
  mounted() {
  	let me = this;
  },
  computed: {
  },
  methods: {
	fnLogin(url) {
		let me = this;
		let returnUrl = url == null ? document.location.href : url;
		sessionStorage.setItem('returnUrl', returnUrl);
		document.location.href = '/user/auth/login';
	},

	fnLogout() {
		let me = this;
		let currUrl = document.location.href;

		axios.get('/user/api/v1/users/logout')
		.then(function(response) {
			let res = response.data;
			if(res.success) {
				document.location.href = currUrl;
			}else{

				alert(res.message);
			}
		})
		.catch(function(error) {
			//modalView.openAlert(error);
		})
	},

	fnJoin() {
		document.location.href = '/user/auth/join';
	},

	fnUpdateUser() {
		document.location.href = '/user/myPage/updateUser';
	},

	fnUpdatePassword() {
		document.location.href = '/user/myPage/updatePassword';
	},

	fnLeave() {
		document.location.href = '/user/myPage/leave';
	},

	// 관리자 이동.
	fnAdmin() {
		let me = this;
		document.location.href = '/admin';
	},

  }
});
