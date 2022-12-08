var position = new Vue({
  el: '#position',
  data: {
  	upperMenu: null,
	lowerMenu: null,
  },
  created() {
  },
  mounted() {

  },
  computed: {
  },
  methods: {
  	fnSetMenu(upperMenu, lowerMenu) {
  		let me = this;
  		me.upperMenu = upperMenu;
  		me.lowerMenu = lowerMenu;
  	}
  },
});
