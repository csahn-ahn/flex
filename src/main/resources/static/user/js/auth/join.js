var app = new Vue({
	el: '#app',
	data: {
		step: 1,
		joinType: 0,
		agreeCheckAll: false,
		agree: {
			agree1: false,
			agree2: false,
		},
		flexContents: [
			{contentId: 'JOIN_AGREE_1', body: ''},
			{contentId: 'JOIN_AGREE_2', body: ''}
		],

		user: {
			username: 'ahnstar83@gmail.com',
			name: '테스트-01',
			password: 'faith83!',
			confirmPassword: 'faith83!',
			hp: '01012345678',
			email: 'test01@gmail.com',
			address1: '',
			address2: '',
			snsType: '',
			snsUid: '',
		},

	},
	created() {
	},
	mounted() {
		let me = this;
		_flexContent.fnContent(me.flexContents);
	},
	watch: {
		agreeCheckAll(value) {
			let me = this;
			if(value == true){
				me.agree.agree1 = true;
				me.agree.agree2 = true;
			} else {
				me.agree.agree1 = false;
				me.agree.agree2 = false;
			}
		}
	},
	computed: {
	},
	methods: {

		fnSocialJoin(channel) {
			var url = '/user/' + channel + '/login';
			var name = 'sociallogin';
			var spec = '';

			switch (channel) {
			  case 'naver':
				spec = 'width=445,height=510,scrollbars=yes';
				break;
			  case 'facebook':
				spec = 'width=620,height=600';
				break;
			  case 'google':
				spec = 'width=620,height=600';
				break;
			  case 'kakao':
				spec = 'width=620,height=600';
				break;
			  default:
				break;
			}

			if(channel != 'kakao') {
				_popup.fnOpenAlert('준비중입니다.');
				return false;
			}else{
				var socialLoginWindow = window.open(url, name, spec);
			}
		},

		fnSocialSuccess(snsType, snsUid) {
			let me = this;
			me.snsType = snsType;
			me.snsUid = snsType;

			me.fnSetJoinType(1);
		},


		fnSetJoinType(type) {
			let me = this;

			if(type == 1) {
				me.step = 2;

			} else if(type == 2) {


			} else if(type == 3) {

			}
		},

		fnNextStep() {
			let me = this;
			if(me.step == 2) {
				// 개인정보 수집 및 이용동의
				if(!me.agree.agree1 || !me.agree.agree2) {
					_popup.fnOpenAlert('동의항목에 모두 동의하셔야 가입할 수 있습니다.');
					return false;
				}
			}

			me.step++;
		},

		fnSave() {
			let me = this;
			axios.post(
				'/user/api/v1/users/join',
				me.user
			)
			.then(function(response) {
				if(response.data.success) {
					//document.location.href = '/';
					me.step++;

				}else{
					_popup.fnOpenAlert(response.data.message);
				}
			})
			.catch(function(error) {
				_popup.fnOpenAlert('error : ' + error);
			})

			return false;
		},

		fnCancel() {
			document.location.href = '/user/auth/join';
		},
	}
});

$(function() {
	$("form").on("submit", function(){
		app.fnSave();
		return false;
	})
})