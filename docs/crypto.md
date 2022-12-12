# 서비스 구성

## 양방향 암호화
- 암호화 기법 : AES256 + salt
- 암호화 / 복호화 예제.
```java
String originText = "암호화 대상 문자열";

// 암호화
String encryptedText = AES256Crypto.encrypt(originText);

// 복호화
String decryptedText = AES256Crypto.decrypt(encryptedText);
```


[< 뒤로가기](../README.md)