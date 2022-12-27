# Flex Application

## 설치 및 실행

### Redis 설치
```yaml
spring:
    session:
        redis:
            flush-mode: ON_SAVE
            namespace: spring:session:flex
    store-type: redis
        redis:
            host: localhost
            port: 6379
            password: ''
```

### Source download
Repository
```shell
git clone https://github.com/csahn-ahn/flex
```
Command
```shell
mvn clean install
```

## 개발가이드
[서비스 구성](./docs/env.md)

[애플리케이션 구성 및 설정](./docs/app.md)

[인증/권한/세션](./docs/auth.md)

[암/복호화](./docs/crypto.md)

[Logger](./docs/logger.md)

[뷰 템플릿엔진](./docs/view.md)

[이벤트](./docs/event.md)

[콘텐츠 연동](./docs/content.md)
