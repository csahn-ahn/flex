var asideMenu = new Vue({
  el: '#asideMenu',
  data: {
    menus: [],
   	current: {
    	upperMenu: null,
    	lowerMenu: null,
    }

  },
  created() {
  },
  mounted() {
  	let me = this;
	me.fnGetMyMenus();
  },
  computed: {
  },
  methods: {

  	// 나의 메뉴 조회
  	fnGetMyMenus() {
  		let me = this;
  		axios({
			method:'get',
			url: '/admin/api/v1/adminGroupMenus/myMenu',
			params: me.search
		})
		.then(function(response) {
			me.menus = response.data;

			var currentUrl = document.location.href;
			var urlArray = currentUrl.split('/');

			upperMenuPath = '/' + urlArray[3] + '/' + urlArray[4];
			lowerMenuPath = upperMenuPath + '/' + urlArray[5];

			me.current.upperMenu = me.menus.filter(menu => menu.upperMenuId == 0 && menu.linkUrl != null && menu.linkUrl != '' && menu.linkUrl.indexOf(upperMenuPath) > -1)[0];
			if(me.current.upperMenu){
				me.current.upperMenu.active = true;
				me.current.lowerMenu = me.current.upperMenu.lowerMenus.filter(menu => menu.upperMenuId > 0 && menu.linkUrl != null && menu.linkUrl != '' && menu.linkUrl.indexOf(lowerMenuPath) > -1)[0];

				if(me.current.lowerMenu){
					me.current.lowerMenu.active = true;
					position.fnSetMenu(me.current.upperMenu, me.current.lowerMenu);
				}
			}
		})
		.catch(function(error) {
			modalView.openAlert(error.message + '1');
		})
  	}
  }
});
