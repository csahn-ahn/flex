# 서비스 구성


| 구분          |          설명           |  비고 |
|-------------|:---------------------:|----:|
| `웹서버`       |        ngineX         |     |
| `WAS`       |    Embedded Tomcat    |     |
| `개발언어`      |    Java (Open jdk)    |     |
| `DB`        |      SQL Server       |     |
| `Framework` | Springboot Framework |     |

## 인프라
| 구분             |                 설명                 |  비고 |
|----------------|:----------------------------------:|----:|
| `버전관리`         |               Gitlab               |     |
| `빌드/배포`        |              Jenkins               |     |
| `메모리DB(캐싱)`    |               Redis                |     |
| `모니터링`         |              Scouter               |     |
| `Notification` | Teams or Slack |     |

## WAS
| 구분          |    설명    |   포트 |
|-------------|:--------:|-----:|
| `개발`        | 개발 서비스용  | 8080 |
| `운영1`       | 운영1 서비스용 | 8081 |
| `운영2`       | 운영2 서비스용 | 8082 |

## 이중화
- 2개이상의 WAS를 구동하여 로드밸런싱 적용.

[< 뒤로가기](../README.md)