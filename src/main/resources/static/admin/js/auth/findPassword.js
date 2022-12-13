var app = new Vue({
  el: '#app',
  data: {
    auth: {
      name: '슈퍼관리자',
      username: 'admin',
      email: 'csahn@univ.me',
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

		axios.post(
			'/admin/api/v1/auth/findPassword',
			me.auth
		)
		.then(function(response) {
			if(response.data.success) {
				modalView.openAlert(
					response.data.message
					,function() {
						document.location.href = '/admin/auth/changeTempPassword?username=' + me.auth.username;
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
		if (!chkFormatEmail(me.auth.email)) {
			modalView.openAlert("이메일 형식에 맞춰주세요.");
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