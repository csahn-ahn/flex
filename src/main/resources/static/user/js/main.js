var app = new Vue({
  el: '#app',
  data: {
	user: null,
	flexContents: [
		{contentId: 'MAIN_CONTENT', body: ''}
	],
  },
  created() {
  },
  mounted() {
  	let me = this;
  	me.init();
  	_flexContent.fnContent(me.flexContents);
  },
  computed: {
  },
  methods: {
  	init() {
  		let me = this;
  	},
  }
});
