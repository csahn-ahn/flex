var summernoteEditor;

function editorInit() {
	summernoteEditor = $('.summernote').summernote({
		//toolbar: [],
		fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New','맑은 고딕','궁서','굴림체','굴림','돋움체','바탕체'],
		fontSizes: ['8','9','10','11','12','14','16','18','20','22','24','28','30','36','50','72'],
		lang: 'ko-KR',
		airMode: false,
	});
}

var app = new Vue({
	el: '#app',
	data: {
		contentId: new URLSearchParams(window.location.search).get('contentId'),
		itemId: new URLSearchParams(window.location.search).get('itemId'),
		item: {
			itemId: 0,
			contentId: new URLSearchParams(window.location.search).get('contentId'),
			serviceStartTime: '',
			serviceEndTime: '',
			title: '',
			body: ''
		},
		isShow: false,
	},
	created() {
	},
	mounted() {
		let me = this;
		me.init();
	},
	watch: {
	},
	methods: {
		init() {
			let me = this;

			if(me.contentId && me.itemId > 0) {
				axios({
					method:'get',
					url: '/admin/api/v1/contents/' + me.contentId + '/items/' + me.itemId,
					params: {}
				})
				.then(function(response) {
					me.item = response.data;
					if(me.item == null || me.item.itemId == null || me.item.itemId == 0) {
						me.fnCancel();
					}
					setTimeout(() => {
						app.isShow = true;
						editorInit();
					}, 500)

				})
				.catch(function(error) {
					modalView.openAlert(error);
				})
			}else{
				me.isShow = true;
				editorInit();
			}
		},

		fnSubmit() {
			let me = this;
			if(me.item.serviceStartTime.length != 19 || me.item.serviceEndTime.length != 19){
				modalView.openAlert('서비스 기간의 값이 형식에 맞지 않습니다.<br>(형식 : YYYY-MM-DD hh:mm:ss)');
				return false;
			}
			me.item.body = summernoteEditor.summernote('code');
			modalView.openConfirm(
				'저장 하시겠습니까?',
				function(){
					axios.post(
						'/admin/api/v1/contents/' + me.contentId + '/items',
						me.item
					)
					.then(function(response) {
						modalView.openAlert(
							'저장 되었습니다.'
							,function() {
								opener.window.location = '/admin/content/contents/view?contentId=' + me.contentId;
								window.close();
							}
						);
					})
					.catch(function(error) {
						modalView.openAlert(error);
					})
				},
				function() {
				}
			);
			return false;
		},

		fnCancel() {
			window.close();
		},
	},
	computed: {
	}
});

$(function() {
	$("form").on("submit", function(){
		app.fnSubmit();
		return false;
	})
})