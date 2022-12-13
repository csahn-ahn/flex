var app = new Vue({
  el: '#app',
  data: {
    auth: {
	  username: new URLSearchParams(window.location.search).get('username'),
      tempPassword: '',
      password: '',
      confirmPassword: '',
    }
  },
  created() {
  },
  mounted() {
  },
  computed: {
  },
  methods: {
  	fnSubmit() {
  		let me = this;

		axios.put(
			'/admin/api/v1/auth/changeTempPassword',
			me.auth
		)
		.then(function(response) {
			if(response.data.success) {
				modalView.openAlert(
					'비밀번호가 변경되었습니다.\n변경된 비밀번호로 로그인하여 주십시오'
					,function() {
						document.location.href = '/admin/login';
					}
				);
			} else {
				modalView.openAlert(
					response.data.message
				);
			}
		})
		.catch(function(error) {
			modalView.openAlert(error);
		});

		return false;
  	},

  	validate() {
		let me = this;

		//이메일 형식 체크.
		if (me.auth.password != me.auth.confirmPassword) {
			modalView.openAlert("비밀번호가 일치하지 않습니다.");
			return false;
		}

		return true;
	},
  }
});

$(function() {
	$("form").on("submit", function(){
		app.fnSubmit();
		return false;
	})
})