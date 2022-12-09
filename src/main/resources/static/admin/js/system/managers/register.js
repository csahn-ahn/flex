var app = new Vue({
	el: '#app',
	data: {
		username: new URLSearchParams(window.location.search).get('username'),
		manager: {
			username: '',
			name: '',
			password: '',
			passwordConfirm: '',
			hp: '',
			email: '',
			groupId: 0
		},
		confirmUsername: '',
		checkId: false,
	},
	created() {
	},
	mounted() {
		let me = this;
		me.init();
	},
	watch: {
		manager: {
			deep: true,
			handler(newObj, oldObj) {
				let me = this;
				if(me.username == null || me.username == ''){

					if(me.confirmUsername != newObj.username) {
						me.checkId = false;
					}
				}
			}
		}
	},
	methods: {
		init() {
			let me = this;

			if(me.username) {
				axios({
					method:'get',
					url: '/admin/api/v1/managers/' + me.username,
					params: {}
				})
				.then(function(response) {
					me.manager = response.data;

					if(me.manager == null || me.manager.username == null) {
						me.fnCancel();
					}
				})
				.catch(function(error) {
					modalView.openAlert(error);
				})
			}

		},

		fnCheckId() {
			let me = this;

			console.log('username : ' + me.manager.username);
			if(me.manager.username == '') {
				modalView.openAlert('아이디를 입력해주세요.');
				return false;
			}

			axios.get('/admin/api/v1/managers/' + me.manager.username)
			.then(function(response) {
				var result = response.data;
				if(result.username != null && result.username != '') {
					modalView.openAlert('사용할 수 없는 아이디 입니다.');
					me.checkId = false;

				}else{
					modalView.openAlert('확인되었습니다. (사용가능)');
					me.confirmUsername = me.manager.username;
					me.checkId = true;
				}
			})
			.catch(function(error) {
				modalView.openAlert('오류가 발생했습니다. 관리자에 문의하여 주시기 바랍니다.<br>[' + error.message + ']');
			})
		},

		fnSubmit() {
			let me = this;
			let title = '등록';

			if(me.username == null || me.username == ''){
				if(!me.checkId) {
					modalView.openAlert('아이디를 확인해주세요.');
					return false;
				}

				if(!me.validate()) {
					return false;
				}
			}else{
				title = '수정';
			}

			modalView.openConfirm(
				title + '하시겠습니까?',
				function(){
					axios.post(
						'/admin/api/v1/managers',
						me.manager
					)
					.then(function(response) {
						modalView.openAlert(
							title + '되었습니다.'
							,function() {
								document.location.href = '/admin/system/managers/register?username=' + response.data.username;
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

		validate() {
			let me = this;

			if(me.manager.password !== me.manager.passwordConfirm) {
				modalView.openAlert('비밀번호가 일치하지 않습니다.');
				return false;
			}

			// 휴대폰 형식 체크
			if(!chkFormatPhone(me.manager.hp)){
				modalView.openAlert("휴대폰 형식에 맞춰 입력해주세요.");
				return false;
			}

			//이메일 형식 체크.
			if (!chkFormatEmail(me.manager.email)) {
				modalView.openAlert("이메일 형식에 맞춰주세요.");
				return false;
			}

			return true;
		},

		fnCancel() {
			document.location.href = '/admin/system/managers/main';
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