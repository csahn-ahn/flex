var app = new Vue({
	el: '#app',
	data: {
		codeGroups: [],
		codes: [],

		searchCodeGroup:{
			page: 1,
			pageSize: 10,
			codeGroupId: '',
			codeGroupName: ''
		},
		searchCode: {
			page: 1,
			pageSize: 10,
			codeGroupId: '',
			codeId: '',
			codeName: ''
		},

		codeGroup: {
			codeGroupId: '',
			codeGroupName: '',
		},
		selectedCodeGroup: {},
		code: {
			codeId: '',
			codeGroupId: '',
			codeValue: '',
			codeName: ''
		},

		isUpdateMode: false,
		isUpdateCodeMode: false,
		beforeCodeGroupName: '',
		beforeCodeName: '',
		beforeCodeValue: '',
	},
	created() {
	},
	mounted() {
		let me = this;
		me.fnSearchCodeGroup();
	},
	methods: {

		// 상위코드 조회
		fnSearchCodeGroup() {
			let me = this;
			me.codeGroups = [];
			me.searchCodeGroup.page = 1;
			me.isUpdateMode = false;
			me.fnGetCodeGroups();
		},

		// 상위코드 목록 조회
		fnGetCodeGroups() {
			let me = this;

			axios({
				method:'get',
				url: '/admin/api/v1/codeGroups',
				params: me.searchCodeGroup
			})
			.then(function(response) {
				me.codeGroups = response.data;

				for(var i=0; i<me.codeGroups.length; i++) {
					me.codeGroups[i].isUpdateMode = false;
				}
			})
			.catch(function(error) {
				modalView.openAlert(error);
			})
		},

		// 일자 포맷 변경.
		convertDateFormat(date) {
			return convertDateFormat(date, 'YYYY.MM.DD HH:mm');
		},

		// 상위코드 등록
		fnCreateCodeGroup() {
			let me = this;
			if(me.codeGroup.codeGroupId == '') {
				modalView.openAlert('상위코드를 입력해주세요.');
				return false;
			}

			if(me.codeGroup.codeGroupName == '') {
				modalView.openAlert('상위코드명을 입력해주세요.');
				return false;
			}

			axios({
				method:'get',
				url: '/admin/api/v1/codeGroups/' + me.codeGroup.codeGroupId,
				params: {}
			})
			.then(function(response) {
				if(typeof response.data == 'object') {
					modalView.openAlert('이미 등록되어 있는 상위코드입니다.');
					return false;
				}

				modalView.openConfirm(
					'상위코드를 등록 하시겠습니까?',
					function(){
						axios.post(
							'/admin/api/v1/codeGroups',
							me.codeGroup
						)
						.then(function(response) {
							if(response.data.success) {
								modalView.openAlert(
									'등록 되었습니다.'
									,function() {
										me.fnSearchCodeGroup();
									}
								);
							}else {
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
			})
			.catch(function(error) {
				modalView.openAlert(error);
			});
		},

		// 상위코드 수정
		fnUpdateCodeGroup(obj) {
        	let me = this;
        	me.beforeCodeGroupName = obj.codeGroupName;
        	obj.isUpdateMode = true;
        	me.isUpdateMode = true;
		},

		// 상위코드 수정취소
		fnCancelUpdateCodeGroup(obj) {
			let me = this;
			obj.codeGroupName = me.beforeCodeGroupName;
			obj.isUpdateMode = false;
			me.isUpdateMode = false;
		},

		// 상위코드 저장(수정)
		fnSaveCodeGroup(obj) {
			let me = this;

			if(obj.codeGroupName == '') {
				modalView.openAlert('상위코드명을 입력하십시오.');
				return false;
			}

			modalView.openConfirm(
				'상위코드를 저장 하시겠습니까?',
				function(){
					axios.post(
						'/admin/api/v1/codeGroups',
						obj
					)
					.then(function(response) {
						if(response.data.success) {
							modalView.openAlert(
								'저장 되었습니다.'
								,function() {
									me.fnSearchCodeGroup();
								}
							);
						}else {
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
		},

		// 상위코드 삭제
		fnDeleteCodeGroup(obj) {
			let me = this;
			modalView.openConfirm(
				'상위코드를 삭제 하시겠습니까?',
				function(){
					axios.delete('/admin/api/v1/codeGroups/' + obj.codeGroupId)
					.then(function(response) {
						if(response.data.success == true){
							modalView.openAlert(
								'삭제 되었습니다.'
								,function() {
									me.fnSearchCodeGroup();
								}
							);
						} else {
							modalView.openAlert(response.data.message);
						}
					})
					.catch(function(error) {
						modalView.openAlert(error.message);
					})
				},
				function() {
				}
			);
		},

		// 하위코드 검색
		fnSearchCode(codeGroupId) {
			let me = this;
			me.codes = [];
			me.code = {
				codeId: '',
				codeGroupId: codeGroupId,
				codeValue: '',
				codeName: ''
			};
			me.searchCode.page = 1;
			me.isUpdateMode = false;
			me.selectedCodeGroup = me.codeGroups.filter(obj => obj.codeGroupId == codeGroupId)[0];
			me.fnGetCodes();
		},

		// 하위코드 목록
		fnGetCodes() {
			let me = this;

			axios({
				method:'get',
				url: '/admin/api/v1/codes/' + me.selectedCodeGroup.codeGroupId,
				params: me.searchCode
			})
			.then(function(response) {
				me.codes = response.data;

				for(var i=0; i<me.codes.length; i++) {
					me.codes[i].isUpdateMode = false;
				}
			})
			.catch(function(error) {
				modalView.openAlert(error);
			})
		},

		// 하위코드 등록
		fnCreateCode() {
			let me = this;
			if(me.code.codeId == '') {
				modalView.openAlert('하위코드를 입력해주세요.');
				return false;
			}

			if(me.code.codeValue == '') {
				modalView.openAlert('하위코드값을 입력해주세요.');
				return false;
			}

			axios({
				method:'get',
				url: '/admin/api/v1/codes/' + me.code.codeGroupId + '/' + me.code.codeId,
				params: {}
			})
			.then(function(response) {
				if(typeof response.data == 'object') {
					modalView.openAlert('이미 등록되어 있는 코드입니다.');
					return false;
				}

				modalView.openConfirm(
					'하위코드를 등록 하시겠습니까?',
					function(){
						axios.post(
							'/admin/api/v1/codes/' + me.code.codeGroupId,
							me.code
						)
						.then(function(response) {
							if(response.data.success) {
								modalView.openAlert(
									'등록 되었습니다.'
									,function() {
										me.fnSearchCode(me.code.codeGroupId)
									}
								);
							}else {
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
			})
			.catch(function(error) {
				modalView.openAlert(error);
			});
		},

		// 하위코드 수정
		fnUpdateCode(obj) {
			let me = this;
			me.beforeCodeName = obj.codeName;
			me.beforeCodeValue = obj.codeValue;
			obj.isUpdateMode = true;
			me.isUpdateCodeMode = true;

			var newCodeList = me.codes;
			me.codes = [];
			me.codes = newCodeList;

			console.log('obj-isUpdateMode : ' + obj.isUpdateMode);
		},

		// 하위코드 수정취소
		fnCancelUpdateCode(obj) {
			let me = this;
			obj.codeName = me.beforeCodeName;
			obj.codeValue = me.beforeCodeValue;
			obj.isUpdateMode = false;
			me.isUpdateCodeMode = false;
		},

		// 하위코드 저장(수정)
		fnSaveCode(obj) {
			let me = this;

			if(obj.codeName == '') {
				modalView.openAlert('하위코드명을 입력하십시오.');
				return false;
			}

			modalView.openConfirm(
				'하위코드를 저장 하시겠습니까?',
				function(){
					axios.post(
						'/admin/api/v1/codes/' + me.selectedCodeGroup.codeGroupId,
						obj
					)
					.then(function(response) {
						if(response.data.success) {
							modalView.openAlert(
								'저장 되었습니다.'
								,function() {
									me.fnGetCodes();
								}
							);
						}else {
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
		},

		// 하위코드 삭제
		fnDeleteCode(obj) {
			let me = this;
			modalView.openConfirm(
				'하위코드를 삭제 하시겠습니까?',
				function(){
					axios.delete('/admin/api/v1/codes/' + obj.codeGroupId + '/' + obj.codeId)
					.then(function(response) {
						if(response.data.success == true){
							modalView.openAlert(
								'삭제 되었습니다.'
								,function() {
									me.fnGetCodes();
								}
							);
						} else {
							modalView.openAlert(response.data.message);
						}
					})
					.catch(function(error) {
						modalView.openAlert(error.message);
					})
				},
				function() {
				}
			);
		},

		isCreate() {
			return hasCreate == 'true' ? true : false;
		},
		isUpdate() {
			return hasUpdate == 'true' ? true : false;
		},
		isDelete() {
			return hasDelete == 'true' ? true : false;
		},
		isDownload() {
			return hasDownload == 'true' ? true : false;
		},
	},
	computed: {
	}
});
