var app = new Vue({
  el: '#app',
  data: {
	user: null,
	flexContents: [
		{contentId: 'STORY_MAIN_CONTENT', body: ''}
	],
  },
  created() {
  },
  mounted() {
  	let me = this;
  	_flexContent.fnContent(me.flexContents);
  },
  computed: {
  },
  methods: {
  }
});
