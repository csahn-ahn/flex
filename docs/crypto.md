# 암/복호화

## 양방향 암호화 (AES256)
```java
String originText = "암호화 대상 문자열";

// 암호화
String encryptedText = AES256Crypto.encrypt(originText);

// 복호화
String decryptedText = AES256Crypto.decrypt(encryptedText);
```

## 단방향 암호화 (SHA256)
```java
String password = "faith83!";
String encryptedPassword = passwordEncoder.encode(password);
```

[< 뒤로가기](../README.md)