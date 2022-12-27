var _popup = new Vue({
  el: '#_popup',
  data: {
  	alert: {
  		message: '',
  		callback: null,
  	},
  	confirm: {
  		message: '',
  		callback: null,
  		cancelCallback: null,
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
	// alert 팝업
	fnOpenAlert(message, callback) {
		let me = this;
		me.alert.message = message;
		me.alert.callback = callback ? callback : null;
		layerPopup.openPopup('systemAlertPopup');
	},

	// confirm 팝업
	fnOpenConfirm(message, callback, failCallback) {
		let me = this;
		me.confirm.message = message;
		me.confirm.callback = callback ? successCallback : null;
		me.confirm.cancelCallback = failCallback ? failCallback : null;
		layerPopup.openPopup('systemConfirmPopup')
	},

	fnAlertCallback() {
		let me = this;
		if(me.alert.callback) {
			me.alert.callback();
		}
	},

	fnConfirmCallback() {
		let me = this;
		if(me.confirm.callback) {
			me.confirm.callback();
		}
	},

	fnConfirmCancelCallback() {
		let me = this;
		if(me.confirm.cancelCallback) {
			me.confirm.cancelCallback();
		}
	},
  }
});
