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
	fnLogin() {
		let me = this;
		let returnUrl = document.location.href;
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

	// 관리자 이동.
	fnAdmin() {
		let me = this;
		document.location.href = '/admin';
	}

  }
});
