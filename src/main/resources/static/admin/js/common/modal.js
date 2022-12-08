var modalView = new Vue({
	el: '#modalWindow',
	data: {
		text: '',
		confirmCallback: null,
		cancelCallback: null,
	},
	created() {
	},
	mounted() {
		let me = this;
		me.init();
	},
	methods: {
		init() {
			let me = this;
			me.text = null;
			me.confirmCallback = null;
			me.cancelCallback = null;
		},
		openConfirm(text, confirmCallback, cancelCallback) {
			let me = this;
			me.text = text;
			me.confirmCallback = confirmCallback;
			me.cancelCallback = cancelCallback;

			$("button[id=btnConfirmModal]").click();
		},
		openAlert(text, callback) {
			let me = this;
			me.text = text;
			me.cancelCallback = callback;
			$("button[id=btnAlertModal]").click();
		},
		fnConfirm() {
			let me = this;
			if(me.confirmCallback != null){
				me.confirmCallback();
			}
			$("button[id=btnConfirmModal]").click();
			me.init();
		},
		fnCancel() {
			let me = this;
			if(me.cancelCallback != null){
				me.cancelCallback();
			}
			me.init();
		},
	},
	computed: {
	}
});
