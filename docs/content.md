# 콘텐츠 연동

### 스크립트
- contentId : 관리자에서 등록한 콘텐츠 식별Id
```
    var app = new Vue({
	el: '#app',
	data: {
		flexContents: [
			{contentId: 'JOIN_AGREE_1', body: ''},
			{contentId: 'JOIN_AGREE_2', body: ''}
		]
    },
    mounted() {
		_flexContent.fnContent(me.flexContents);
	}
});
```
### 마크업
```html
<div id="app">
	<div v-text="flexContents[0].body"></div>
	<div v-text="flexContents[1].body"></div>    
</div>
```

[< 뒤로가기](../README.md)