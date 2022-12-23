var app = new Vue({
	el: '#app',
	data: {
		username: new URLSearchParams(window.location.search).get('username'),
		manager: {
			username: 'chris83',
			name: '안치성',
			hp: '01022820317',
			email: 'csahn@univ.me',
			groupId: 1
		},
		confirmUsername: '',
		checkId: false,
		groups: [],
	},
	created() {
	},
	mounted() {
		let me = this;
		me.init();
		me.fnGetGroups();
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

		fnGetGroups() {
			let me = this;
			axios
			.get('/admin/api/v1/adminGroups')
			.then(function(response) {
				me.groups = response.data;
				me.groups.reverse();

				if(me.username == null) {
					me.manager.groupId = me.groups[0].groupId;
				}
			})
			.catch(function(error) {
				modalView.openAlert('오류가 발생했습니다. 관리자에 문의하여 주시기 바랍니다.<br>[' + error.message + ']');
			})
		},

		fnCheckId() {
			let me = this;

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
					modalView.openAlert('사용할 수 있는 아이디입니다.');
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
			let title = '초대';

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
				title + ' 하시겠습니까?',
				function(){
					axios.post(
						'/admin/api/v1/managers',
						me.manager
					)
					.then(function(response) {
						modalView.openAlert(
							title + ' 되었습니다.'
							,function() {
								document.location.href = '/admin/system/managers/main';
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