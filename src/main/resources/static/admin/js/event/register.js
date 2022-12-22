var app = new Vue({
	el: '#app',
	data: {
		eventId: new URLSearchParams(window.location.search).get('eventId'),
		event: {
			eventId: 0,
			title: '',
			description: ''
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

			if(me.eventId != '') {
				axios({
					method:'get',
					url: '/admin/api/v1/events/' + me.eventId,
					params: {}
				})
				.then(function(response) {
					me.event = response.data;

					if(me.eventId == 0 || me.event.eventId == 0) {
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
			let title = me.eventId > 0 ? '수정' : '등록';

			modalView.openConfirm(
				title + ' 하시겠습니까?',
				function(){
					axios.post(
						'/admin/api/v1/events',
						me.event
					)
					.then(function(response) {
						if(response.data.success){
							modalView.openAlert(
								title + ' 되었습니다.'
								,function() {
									document.location.href = '/admin/event/events/main';
								}
							);
						}else{
							modalView.openAlert(response.data.message);
						}
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
			document.location.href = '/admin/event/events/main';
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