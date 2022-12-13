# 외부 연동

## 썬더메일
```java
private final MailService mailService;
private final FlexProperties flexProperties;

Map<String, Object> variables = new HashMap<>();
variables.put(EmailParameterKey.EPK_URL_PREFIX, flexProperties.getEmailProps().getAdminUrl());
mailService.send("ahnstar83@gmail.com", EmailTemplateEnum.SITE_QNA_ANSWER, variables, "안치성");
```

## OTP
```java

```