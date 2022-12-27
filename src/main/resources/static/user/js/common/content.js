var _flexContent = new Vue({
  el: '#content',
  data: {


  },
  created() {
  },
  mounted() {
  	let me = this;
  	//me.init();
  },
  computed: {
  },
  methods: {
	init() {
		let me = this;

		$("[data-flex-content]").each(function(obj) {
			console.log('test');
			var content = {contentId: $(this).attr('data-flex-content'), html: ''}
			me.contents.push(content);
		});
		console.log(JSON.stringify(me.contents));

		axios({
			method:'get',
			url: '/admin/api/v1/contents',
			params: {}
		})
		.then(function(response) {
			me.contents = response.data;
		})
		.catch(function(error) {
			_popup.fnOpenAlert(error);
		})
	},

	fnGetContent(contentId, callback) {
		console.log('contentId : ' + contentId);

		axios({
			method:'get',
			url: '/user/api/v1/contents/' + contentId,
			params: {}
		})
		.then(function(response) {
			callback(response.data)
		})
		.catch(function(error) {
			_popup.fnOpenAlert(error);
		})
	},

	/**********************************
	contents: [
		{contentId: 'JOIN_AGREE_1', html: '내용 업데이트1'},
		{contentId: 'JOIN_AGREE_2', html: '내용 업데이트2'}
	],

	***********************************/
	fnContent(contents) {
		var previewMode = new URLSearchParams(window.location.search).get('preview');
		var isPreview = previewMode == null ? false : true;
		if(contents != null && Array.isArray(contents)) {
			var contentId = '';
			for(var i=0; i<contents.length; i++) {
				var obj = contents[i];
				contentId += contentId == '' ? obj.contentId : ',' + obj.contentId;
			}
			axios({
				method:'get',
				url: '/user/api/v1/contents',
				params: {
					isPreview: isPreview,
					contentId: contentId
				},
			})
			.then(function(response) {
				var list = response.data;
				for(var i=0; i<list.length; i++) {
					var item = list[i];
					if(contents.filter(obj => obj.contentId == item.contentId).length > 0) {
						var content = contents.filter(obj => obj.contentId == item.contentId)[0];
						content.body = item.body;
					}
				}
			})
			.catch(function(error) {
				_popup.fnOpenAlert(error);
			})

		} else {
			console.log('배열이 아님');
		}
	}
  }
});
/*
var flexContents = [];
$(function() {
	_getFlexContents();
})

function _getFlexContents() {
	if($("[data-flex-content]").length == 0) {
		return false;
	}


	$("[data-flex-content]").each(function(idx) {
		var content = {contentId: $(this).attr('data-flex-content'), html: ''}
		flexContents.push(content);
	});

	axios({
		method:'get',
		url: '/admin/api/v1/contents',
		params: {}
	})
	.then(function(response) {
		me.contents = response.data;
	})
	.catch(function(error) {
		_popup.fnOpenAlert(error);
	})
}
*/