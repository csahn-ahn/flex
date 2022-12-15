var app = new Vue({
  el: '#app',
  data: {
  	exception: new URLSearchParams(window.location.search).get('exception'),
    manager: {
      username: 'admin',
      password: '1234',
    }
  },
  created() {
  },
  mounted() {
  	let me = this;
  	me.init();
  },
  computed: {
  },
  methods: {
  	init() {
  		let me = this;
  		if(me.exception != null && me.exception != '') {
  			alert('로그인을 실패하였습니다.\n(' + me.exception + ')');
  		}
  	}
  }
});
