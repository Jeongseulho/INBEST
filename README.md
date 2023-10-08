# S09P22D110 주렁주렁팀 프로젝트

## 🔍프로젝트 소개

### 📌서비스 요약

- 모의 투자 시뮬레이션 제공
- 자신에게 맞는 자산 관리 방법 제공

### 📌기획 의도

- 초보자들이 주식에 대한 두려움을 줄이고, 주식에 대해 쉽게 배울 수 있는 서비스를 제공
- 국내, 해외, 가상화폐 등 다양한 종목을 제공하여 투자의 폭을 넓힘, 금융 상품과 주식에 대한 정보를 제공하여 자산 관리 방법을 제공

### 📌기술 스택

<img src="https://img.shields.io/badge/typescript-3178C6?styleflat&logo=typescript&logoColor=white">
<img src="https://img.shields.io/badge/reactquery-FF4154?styleflat&logo=reactquery&logoColor=white">
<img src="https://img.shields.io/badge/vite-646CFF?styleflat&logo=vite&logoColor=white">
<img src="https://img.shields.io/badge/react-61DAFB?styleflat&logo=react&logoColor=white">
<img src="https://img.shields.io/badge/tailwindcss-06B6D4?styleflat&logo=tailwindcss&logoColor=white">

<img src="https://img.shields.io/badge/springboot-6DB33F?styleflat&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/springsecurity-6DB33F?styleflat&logo=springsecurity&logoColor=white">

### 📌협업 및 배포 툴

<img src="https://img.shields.io/badge/figma-F24E1E?styleflat&logo=figma&logoColor=white">
<img src="https://img.shields.io/badge/gitlab-FC6D26?styleflat&logo=gitlab&logoColor=white">
<img src="https://img.shields.io/badge/docker-2496ED?styleflat&logo=docker&logoColor=white">
<img src="https://img.shields.io/badge/jenkins-D24939?styleflat&logo=jenkins&logoColor=white">
<img src="https://img.shields.io/badge/Notion-000000?styleflat&logo=Notion&logoColor=white">
<img src="https://img.shields.io/badge/mattermost-0058CC?styleflat&logo=mattermost&logoColor=white">
<img src="https://img.shields.io/badge/jira-0052CC?styleflat&logo=jira&logoColor=white">

### 📌프로젝트 기간

- 2023.08.21 ~ 2023.10.06

## 🙍팀원 소개

- 김수현 : 팀장, 인프라, 금융사전, 자유게시판, 모의투자
- 고예림 : 인프라, 채팅, 알림
- 이호찬 : 랭킹, 로그인/회원가입, 인증/권한
- 장진욱 : 기업 뉴스, 감정분석, 재무제표
- 권종률 : 로그인/회원가입, 랭킹, 자유게시판, 금융사전
- 정슬호 : 모의투자, 채팅, 알림

## 📑Convention

### 📌Git Commit Convention

#### 1. 커밋 유형

- 커밋 제목 첫 글자는 대문자로 작성하기

  | 커밋 유형 | 의미 |
  | --- | --- |
  | Feat | 새로운 기능 추가 |
  | Fix | 버그 수정 |
  | Docs | 문서 수정 |
  | Style | 코드 formatting, 세미콜론 누락, 코드 자체의 변경이 없는 경우 |
  | Refactor | 코드 리팩토링 |
  | Test | 테스트 코드, 리팩토링 테스트 코드 추가 |
  | Chore | 패키지 매니저 수정, 그 외 기타 수정 ex) .gitignore |
  | Design | CSS 등 사용자 UI 디자인 변경 |
  | Comment | 필요한 주석 추가 및 변경 |
  | Rename | 파일 또는 폴더 명을 수정하거나 옮기는 작업만인 경우 |
  | Remove | 파일을 삭제하는 작업만 수행한 경우 |
  | !BREAKING CHANGE | 커다란 API 변경의 경우 |
  | !HOTFIX | 급하게 치명적인 버그를 고쳐야 하는 경우 |

#### 2. 제목과 본문을 빈행으로 분리

- 커밋 유형 이후 제목과 본문은 한글로 작성하여 내용이 잘 전달될 수 있도록 할 것
- 본문에는 변경한 내용과 이유 설명 (어떻게보다는 무엇 & 왜를 설명)

#### 3. 제목 첫 글자는 대문자로, 끝에는 `.` 금지

#### 4. 제목은 영문 기준 50자 이내로 할 것

#### 5. 무엇을 왜 했는지 적기, 어떻게 했는지 적지 않기

```
<타입> : <제목>

내용

날짜 작성자
20230904 yeLim
```

### 📌Git Branch Convention

#### 규칙

- 브랜치 이름 규칙
  - `작업종류/기능` 으로 브랜치 만들기
  - (지라 스토리 이슈 = 기능 명세서 소 분류 = 브랜치 1개)
  - (지라 작업 이슈 = 커밋 1개)
  - `design/FE-logout` `feat/BE-signup` `fix/FE-not-render-nav`
- 해당 기능의 브랜치에 작업이 완료 되면 해당 브랜치를 원격 저장소에 `push`하고 git Lab 페이지에서 `merge request(source : 본인 기능 브랜치, target : master)`(로컬에서 그냥 `merge` X)
  - `**merge request` 오픈 이벤트 발생 시 EC2에서 빌드, 배포 실행 ⇒ MM으로 결과 알림
- `merge request` 위 결과에 따라 승인 여부 결정, 필요한 경우 코드 리뷰 및 토의
- `merge request`가 승인되면 `merge된 master` 브랜치에대하여 다시 EC2에서 빌드, 배포 실행 ⇒ MM으로 결과 알림

  - EC2에서 테스트 코드 실행 / 빌드(번들링 등의 파일 빌드) / 배포 진행 ⇒ 메신저로 결과 알림

- 머리 수락되면 merge된 develop 브랜치 기준으로 CI/CD 진행

#### 구조

`master`

- `feature-{서버 명}-{작업명}`
- `develop-front` : fe 서버
  - `feature-front-login` : fe서버에서 로그인 기능을 만드는 브랜치
- `develop-user` : user 서버
  - `feature-user-login` : 유저 서버에서 로그인 기능을 만드는 브랜치
  - `feature-user-join`
  - `feature-user-mypage`
- `develop-chatbot` : 챗봇 서버
  - `feature-chatbot-crawling` :
- `develop-news` : 뉴스 서버
  - `feature-news-crawling`
  -

### 📌Code Convention

#### 프론트엔드 코드 컨벤션

- 컴포넌트 생성은 rafce 스니펫을 사용하여 생성

```typescript
const test = () => {
  return <div>test</div>;
};
export default test;
```

컴포넌트가 아닌 다른 파일은 export default 대신 export 사용

```
export const module = () => {
  return 'module';
};
```

- 객체 타입 선언시 interface, 그외 type 사용, 타입은 파스칼 케이스 사용

```typescript
interface Name {
  name: string;
}

type Code = number | string;
```

- props 타입을 해당 컴포넌트 상위에 선언(interface 사용)

```typescript
interface TestPropsType = {
  name: string;
};

const test = ({ name }: TestPropsType ) => {
  return <div>test</div>;
};

export default test;
```

- 기타 eslint 설정

```javascript
"max-params": ["error", 4], // 메소드 파라미터는 최대 4개 이하
"max-depth": ["error", 3], // 들여쓰기 깊이는 최대 3칸
"quotes": ["error", "double"], // 문자열을 처리할 때는 쌍따옴표를 사용하도록 합니다.
"semi": ["error", "always"], // 문장이 종료될 때는 세미콜론을 붙여줍니다.
"arrow-parens": ["error", "always"], // 기본적으로 모든 함수를 화살표 함수 사용
```

#### 백엔드 코드 컨벤션

### 1. CRUD

- **(C) 삽입**: insertXxxx
- **(R) 읽기**: findXxxx vs getXxxx
- **(U) 수정**: updateXxxx
- **(D) 삭제**: deleteXxxx

### 2. 클래스명

- **Controller**
  - XxxxController
  - 첫 url별로 세분화 하되, 첫 클래스명은 UrlController 형태를 따른다.
- **Service**
  - XxxxService
- **Repository**
  - XxxxRepository
- **Entity**
  - XxxxEntity
- **Dto**
  - XxxxDto

### 3. 패키지 계층(도메인 층)

- **Config**
  - 최상위 패키지 바로 아래에 config 패키지 생성
- controller
- dto
- entity
- exception
- repository
- service
  - 여기서 발생하는 에러를 try-catch가 아니라 controllerAdvice에서 처리하도록

1. DTO 루트 부모 객체를 상속받아 DTO 생성

```jsx
class RootDTO{ // 가명
	 Long seq // pk
	 LocalDateTime createTime ; // 생성시간
	 LocalDateTime updateTime ; // 수정시간
   LocalDateTime deleteTime ; // 삭제시간

  public setCreateTime(){
		// 현재시간을 createTime에 넣기
	}
	public setUpdateTime (){
		// 수정 시간을 createTime에 넣기
	}
	public setDeleteTime (){
		// 삭제 시간을 createTime에 넣기
	}
}

// 만약 유저를 만들면
public User extends RootDTO{

}
```

1. 배포/개발 용 properties 파일 구분
2. properties 파일 암호화 방식 찾기
3. stream방식 주석 잘행

## 🔧설계

### 📌아키텍처 설계

![image](https://github.com/Jeongseulho/portfolio/assets/110578739/6e846690-db85-4b73-ba57-3a332e6728a6)

## 💻주요 화면 스크린샷, gif

<img width="1280" alt="image" src="https://github.com/Jeongseulho/CSstudy/assets/110578739/8ff27851-c786-4779-81b4-34bd35df7811">

## 마이페이지, 전적

![마이페이지,전적](https://github.com/Jeongseulho/CSstudy/assets/110578739/357a2a9d-204e-4957-9f32-957fcf947dd8)

### 로그인 회원가입

![로그인회원가입](https://github.com/Jeongseulho/CSstudy/assets/110578739/28403132-da96-4aa0-a29c-8ba05e1c7ef0)

## 랭킹

### 개인 랭킹

<img width="1280" alt="image" src="https://github.com/Jeongseulho/CSstudy/assets/110578739/c9871109-ffcb-4b82-b6d9-f8a91e181a8d">

### 그룹 랭킹

<img width="1280" alt="image" src="https://github.com/Jeongseulho/CSstudy/assets/110578739/bf09a3fb-c414-41c1-8f7e-0fb33cdd461c">

## 그룹 관리

### 그룹 생성

![그룹 생성](https://github.com/Jeongseulho/CSstudy/assets/110578739/533032e5-30ca-4456-a922-fdd0f3c41727)

### 참여가능 그룹 필터링

![필터모달](https://github.com/Jeongseulho/CSstudy/assets/110578739/2c90334d-9d87-4194-adc7-1063c09ca3d3)

### 초대 알림

![초대알림](https://github.com/Jeongseulho/CSstudy/assets/110578739/c3f7abb8-533a-448f-afc6-4b27b322e172)

### 그룹 참여

![참여모달](https://github.com/Jeongseulho/CSstudy/assets/110578739/ee631462-8adb-49cc-8fc4-ff0b12a46881)

### 그룹채팅

![그룹채팅](https://github.com/Jeongseulho/CSstudy/assets/110578739/8eea3274-3267-484e-a738-69ffc5979c06)

## 게시판

### 게시글 등록

![게시글등록](https://github.com/Jeongseulho/CSstudy/assets/110578739/32399609-6b59-4b46-9c09-7aa9c57e95db)

### 댓글 등록

![댓글등록](https://github.com/Jeongseulho/CSstudy/assets/110578739/3bbe6aa7-68e1-458e-bc8d-c4672a91e62e)

### 대댓글 등록

![대댓글등록](https://github.com/Jeongseulho/CSstudy/assets/110578739/a989befa-303b-4e30-8e94-b7967864e9e7)

## 금융 사전

![금융사전](https://github.com/Jeongseulho/CSstudy/assets/110578739/79f09d44-92ab-405a-a6d8-4ed1768f0c7b)

## 모의투자

### 모의투자 그룹현황

![모의투자그룹현황](https://github.com/Jeongseulho/CSstudy/assets/110578739/9cbcf7bd-99cd-4d70-804f-bf3a7c6ba362)

### 모의투자 내현황

![모의투자내현황](https://github.com/Jeongseulho/CSstudy/assets/110578739/2c2a7dfb-dbd2-48a1-9873-c8a1db671b79)

### 주요주식목록

![주요주식목록](https://github.com/Jeongseulho/CSstudy/assets/110578739/8b07d7d2-bfd1-4543-a3e0-83cf810cd290)

### 뉴스

![주요뉴스,속보,산업별뉴스](https://github.com/Jeongseulho/CSstudy/assets/110578739/79b62a50-8588-490f-9852-5c0606ac503d)

### 기업 상세정보

![기업상세정보](https://github.com/Jeongseulho/CSstudy/assets/110578739/3397468d-c61d-4fa1-b66d-208179525077)

### 캔들차트 튜토리얼

![캔들차트튜토리얼](https://github.com/Jeongseulho/CSstudy/assets/110578739/fe46f1f7-80d4-464d-a69e-501f8e59597f)

### 주식 주문

![주식주문](https://github.com/Jeongseulho/CSstudy/assets/110578739/2d3d3437-40dd-429d-b174-ce846d64c90c)

### 체결 알림

![체결알림](https://github.com/Jeongseulho/CSstudy/assets/110578739/8d08803c-7d8f-4539-8912-ef68b4c1e25d)
