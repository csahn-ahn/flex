var app = new Vue({
  el: '#app',
  data: {
	user: null,
  },
  created() {
  },
  mounted() {
  	let me = this;
  	me.init();
  	me.fnGetMyInfo();
  },
  computed: {
  },
  methods: {
  	init() {
  		let me = this;
  	},

  	fnGetMyInfo() {
		let me = this;
		axios.get('/user/api/v1/users/me')
		.then(function(response) {
			me.user = response.data;
		})
		.catch(function(error) {
			//modalView.openAlert(error);
		})
	},

	fnLogout() {
		let me = this;
		axios.get('/user/api/v1/users/logout')
		.then(function(response) {
			let res = response.data;
			if(res.success) {
				me.user = null;
			}else{
				alert(res.message);
			}
		})
		.catch(function(error) {
			//modalView.openAlert(error);
		})
	}

  }
});
