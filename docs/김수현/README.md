### 09/04

**Spring Config Server 구축**

    - 포트번호 9090
    - yml 양식 {서비스이름}-{개발|배포}
    - http://13.124.142.120:9090 로 배포
    - develop/game으로 예제 client 생성

### 09/11

**1)리눅스 환경설정**

    - 도커 설치
    - 젠킨스 설치
    - 포트 개방
        - Spring Config 서버 (9091)
        - http (80)
        - Jenkins (8081)


**2) CI/CD 구축**

    - CD 결과 mattermost 연동
    - github 프로젝트인 Spring Config 서버 CI/CD 구축

    
**3) 디버깅**
    
    - eureka.client.service-url -> eureka.client.serviceUrl로 수정하여 enpoints 변경 오작동 해결

### 09/12

**프론트엔드 서버 구축 및 배포**

    - Nginx 컨테이너 배포
    - Jenkinx로 CD 구축
    - .env파일은 Spring Config에 frontend-prod.properties로 관리
    - CD를 진행할 때 REST API로 Config 서버에 호출하여 .env파일로 변경하여 프론트엔드 서버에 적용


### 09/13

**1) 기능 명세서 작성**

**2) 뉴스 서버 배포**

    - 파이썬 가상환경 구축
    - Jenkinx에서 뉴스 프로젝트를 ssafy ec2에서 프리티어 ec2로 전송기능
    - 뉴스 서버 Spring Eureka에 등록

