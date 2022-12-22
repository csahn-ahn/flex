var _event = new Vue({
	el: '#event',
	data: {
		event: {
			eventId: 0,
			etc1: '',
			etc2: '',
			etc3: ''
		}
	},
	created() {
	},
	mounted() {
		let me = this;
	},
	computed: {
	},
	methods: {
		fnApplyEvent(eventId) {
			if(_user.username == '') {
				if(!confirm('로그인 후 신청하실 수 있습니다.\n신청 페이지로 이동하시겠습니까?')){
					return false;
				}


				_common.fnLogin(document.location.href);
				return false;
			}

			let me = this;
			if(!confirm('신청 하시겠습니까?')){
				return false;
			}

			axios.post(
				'/user/api/v1/events/apply',
				{
					eventId: eventId,
					etc1: '',
					etc2: '',
					etc3: ''
				}
			)
			.then(function(response) {
				if(response.data.success){
					alert('신청 되었습니다.');
					document.location.href = '/';
				}else{
					alert(response.data.message);
				}
			})
			.catch(function(error) {
				alert(error);
			});
		},
	}
});
