var app = new Vue({
	el: '#app',
	data: {
		contentId: new URLSearchParams(window.location.search).get('contentId'),
		content: {
			contentId: 'TEST_CONTENT_',
			contentType: 1,
			title: '테스트 콘텐츠 ',
			description: '',
			url: '/intro'
		},
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

			if(me.contentId) {
				axios({
					method:'get',
					url: '/admin/api/v1/contents/' + me.contentId,
					params: {}
				})
				.then(function(response) {
					me.content = response.data;
					if(me.content == null || me.content.contentId == null) {
						me.fnCancel();
					}
				})
				.catch(function(error) {
					modalView.openAlert(error);
				})
			}
		},

		fnSubmit() {
			let me = this;

			modalView.openConfirm(
				'저장 하시겠습니까?',
				function(){
					axios.post(
						'/admin/api/v1/contents',
						me.content
					)
					.then(function(response) {
						modalView.openAlert(
							'저장 되었습니다.'
							,function() {
								document.location.href = '/admin/content/contents/view?contentId=' + me.content.contentId;
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
			document.location.href = '/admin/content/contents/main';
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