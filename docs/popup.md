# 팝업

### Alert 팝업
```
    // 함수
    _popup.fnOpenAlert(message, callback)
    
    // 메시지만 노출할 경우.
    _popup.fnOpenAlert('회원가입 되었습니다.');
    
    // 경고창 확인 후 특정 처리가 필요할 경우.
    _popup.fnOpenAlert('회원가입 되었습니다.', () => {
        document.location.href = '/';
    );
```

### Confirm 팝업
```
    // 함수
    _popup.fnOpenConfirm(message, callback, cancelCallback)
    
    // 확인 이벤트가 필요할 경우.
    _popup.fnOpenConfirm(
        '회원가입 되었습니다.', 
        () => {
            // 확인에 대한 처리.
        }
    );
    
    // 확인, 취소 이벤트가 필요한 경우.
    _popup.fnOpenConfirm(
        '회원가입 되었습니다.', 
        () => {
            // 확인에 대한 처리.
        },
        () => {
            // 취소에 대한 처리.
        }
    );
```


[< 뒤로가기](../README.md)