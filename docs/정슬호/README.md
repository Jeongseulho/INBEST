# 2주차

<details>
<summary>접기/펼치기</summary>

#### 2023/9/4(월)

# 📌what i did
- 프로젝트 생성, 세팅 및 폴더 구조 만들기
- 피그마 만들기

# 📌issue & solution
## 📖 폴더 구조 수정
### 💢 issue
ATOMIC 패턴과  Component - custom hooks 패턴 합치면서 hooks 폴더에 atoms, molecules, organisms, page 로직 hook을 모으려고 했는데 이러면 너무 파일이 많아 질 수 있었다.
### 👀 solution
로직을 구분하는 hook이 사실상 재사용 되지는 않고 단지 view, logic을 구분하는데 의의가 있다고 생각해서 각 atoms, molecules, organisms, page 폴더에 컴포넌트 view 부분과 logic 파일을 같이 두기로 하였다.

# 📌to do
- 피그마 계속

#### 2023/9/5(화)

# 📌what i did
- 기본 ATOMIC 구조 예시 생성
- 피그마 만들기

# 📌issue & solution
## 📖 공통 Input.tsx 만들기 
### 💢 issue
원래는 공통 Input.tsx을 만들고 props로 password인지, 일반 default값으로 text인지 선택하여 다른 곳에서 공통으로 쓰려고 했는데, 삼항 연산자 또는 if문으로 분기를 해줘야해서 나눌지 공통으로 1개의 파일에 쓸지 고민했다.
### 👀 solution
결론은 나누기로 했다, input type이 text or password 말고도 email, 또는 search bar 인경우도 있으므로 나누는게 맞다고 생각했다.

# 📌to do
- 피그마 계속


#### 2023/9/6(수)

# 📌what i did
- 공통 컴포넌트 만들기
- 전역 CSS 변수 설정
- 홈 화면 만들기

# 📌what i learned
## 📖 CSS 통일하기
공통때는 이미지로 css를 대체한게 많아서 전역 CSS나 테마를 깊게 고민하지 않았는데, 이제 CSS가 중요해지면서 폰트나 color 변수등을 토의하면서 여러가지 정하였다.
화면에따라 사용할 폰트나 색상이 생각보다 더 다양해서 고민하고 정하는데 오래 걸렸다.

# 📌to do
- 홈 화면 구현


#### 2023/9/7(목)

# 📌what i did
- 홈화면 만들기

# 📌what i learned
## 📖 ATOMIC 패턴 회의감
ATOMIC 패턴의 의미는 재사용성에 있다고 하는데, 피그마를 다 그리고 본격적으로 개발을 시작하다보니 재사용성이 가능한 동일한 CSS의 컴포넌트들이 매우 적은 것을 느꼈다.
그러다보니 작게 나누어서 재사용성을 추구하기 보다는 한 컴포넌트가 너무 비대해지지 않도록 어느정도 컴포넌트를 나누는 기준을 세웠다는 것에 의의를 두어야 겠다고 생각했다.

리액트에서 많이 들어본 패턴이지만 아마 프로젝트 크기가 매우 큰 경우에만 의미가 있는 것 같다, 우리 프로젝트에서는 재사용성 보다 컴포넌트를 나누는 기준을 세웠다는 것에 의의를 두자.

# 📌to do
- 코드 리뷰
- 홈화면 마무리
- 모의 투자 부분 API 명세서 작성


#### 2023/9/8(금)

# 📌what i did
- 피그마 피드백 및 수정

# 📌what i learned
## 📖 UI/UX 고민
주식 및 투자라는 그래프와 숫자가 매우 많은 화면을 그리다보니 레이아웃에대한 고민을 굉장히 많이 하게 된다. 
게다가 초심자를 타겟팅으로 하여서 우리 프로젝트에 사실상 UI가 메인이고 가장 중요한 요소인 것 같다.

원래 나는 그래프나 차트 등을 실제 널린 주식 사이트처럼 보여주고 튜토리얼 및 도움말로 최대한 초심자에게 설명해준다는 느낌으로 했는데, 팀장님의 피드백, 의견은 차트나 그래프를 강조하기보다 해석, 정리한 요약 정보들을 시각적으로 간단하게 보여주자고 하였다.

팀장님 피드백을 받아드려서 전체적인 분위기 자체를 초심자가 보기 어려운 차트, 그래프 등에서 그냥 해석한 요약본 정보들을 보여주는 느낌으로 주말동안 수정해야겠다.

공통때는 피그마를 많이 안해서 몰랐고, 생각해보면 지금 프로젝트처럼 보여줄 정보가 많지 않았다, 게다가 대부분을 그냥 이미지로 써버려서 css할 부분도 적었는데 이번 프로젝트에는 확실히 UI/UX, 디자인, css를 많이 다루게 될 것 같다.

# 📌to do
- 피그마 수정

</details>

# 3주차

<details>
<summary>접기/펼치기</summary>

#### 2023/9/11(월)

# 📌what i did
- 피그마 피드백 및 수정
- 모의 투자 화면 atoms 컴포넌트 만들기

# 📌to do
- 피그마 수정
- 모의 투자 화면 컴포넌트 개발 계속

#### 2023/9/12(화)

# 📌what i did
- 모의 투자 화면 atoms 컴포넌트 만들기
- 모달 라이브러리 선택 및 디폴트 디자인 구현

# 📌to do
- 피그마 수정, 기능 명세서 작성

#### 2023/9/13(수)

# 📌what i did
- 기능 명세서 및 피그마 최종 완성
- modal 로직 및 컴포넌트 구조 구상

# 📌to do
- 모의 투자 화면 컴포넌트 개발 계속

#### 2023/9/14(목)

# 📌what i did
- 모의 투자 그룹 생성 모달 UI 및 로직

# 📌issue & solution
## 📖 그룹 생성 진행도 UI
### 💢 issue
나는 모의 투자 그룹 생성을 모달로 진행하고 모달이 띄워져있는 채로 다음단계 버튼을 누르면서 모달 내부 내용만 바뀌도록 했다.
여기서 현재 그룹 생성의 진행도(단계)를 표현하기 위해서 border만 있고 가운데가 빈 동그라미에 동그라미 border가 진행도만큼 원을 따라 차오르면서 진행도를 표현했다.
근데 진행도가 자연스럽게 차오르도록 애니메이션을 주려면 진행도 UI는 언마운트 없이 상태만 바뀌어야하고 모달 내부 내용들은 언마운트되고 새로운 내용으로 마운트되어야 했다.
### 👀 solution
진행도 UI 파일을 따로 구분하여 만들고 모달 내용 하위 컴포넌트가 아니라 모달 내용 + 진행도 구조로 하여서 진행도는 언마운트 없이 항상 보여지면서 상태만 변화, 모달 내용은 언마운트/마운트 되도록 했다.

# 📌what i learned
## 📖 useState대신 useReducer
```typescript
export type Period = "linkingMode" | 7 | 14 | 30;
export type SeedMoney = "accelerateMode" | 10000000 | 50000000 | 100000000 | 500000000 | 1000000000;

export interface GroupSetting {
  period: Period;
  seedMoney: SeedMoney;
}
```

```typescript
  const reducer = (
    groupSetting: GroupSetting,
    action: { type: string; payload?: Period | SeedMoney }
  ): GroupSetting => {
    switch (action.type) {
      case "PERIOD":
        return { ...groupSetting, period: action.payload as Period };
      case "SEED_MONEY":
        return { ...groupSetting, seedMoney: action.payload as SeedMoney };
      case "RESET":
        return initGroupSetting;
      default:
        throw new Error("Unhandled group setting action");
    }
  };

```
위처럼 useReucer를 사용해서 객체 형태의 상태를 다루었다. 
실제 사용시에는 이렇게만 하면 된다.
```typescript
dispatch({ type: "PERIOD", payload });
```

만약 useState로 해당 객체 형태의 상태를 다룬다면
```typescript
setGroupSetting((prevGroupSetting) => ({ ...prevGroupSetting, period: payload }))
```

지금으로는 간단한 객체 형태에 setState를 하는 형태도 단순해서 별 차이가 없지만 setState 할때, 단순히 1개의 속성값만 바꾸는게 아니고 복잡하게 바꾸어야 할때 추상화하기 좋은 것 같다.
또한 복잡하게 바꾸어야하는 로직이 자주 사용된다면 재사용성에도 좋을 것 이다.
내 예시에서는 둘다 해당되지 않아서 useState나 useReducer나 상관없을 듯

# 📌to do
- 그룹 생성 모달 로직 계속


#### 2023/9/15(금)

# 📌what i did
- 모의 투자 그룹 생성 모달 UI 및 로직

# 📌issue & solution
## 📖 그룹 생성 진행도 UI - 2
### 💢 issue
css만으로 원의 border만 원을 따라서 25%씩 늘어나도록(자연스러운 애니메이션 추가)하는게 좀 어려웠다.
spinner 예시만 많고 진행도 애니메이션을 방법을 못찾아서 border-top만 주고 rotate로 돌리고 하면 처음 25%는 만들 수 있었는데 그 border가 자연스럽게 늘어나면서 마지막에 상하좌우 border가 모두 덮게하는 css가 불가능했다.
### 👀 solution
border-top만 준 태그를 4개 겹치도록 두고 2단계가 되면 3개를 rotate, 3단계가 되면 2개를 rotate, 마지막 단계에서는 1개를 rotate해서 실제 border가 늘어나는건 아니고 움직이기만 하면서 늘어나는 느낌이 나도록 만들었다.
이 방법 생각해내는데 좀 오래 걸렸다.

# 📌what i learned
## 📖 react query 에러 핸들링
내가 원래 하는 에러 핸들링은 `try, catch`에서 cath에서 해결하는 것이었는데, useQuey를 쓰면서 에러핸들링을 하도록 도와주는 방법이 있길래 한번 사용해보았다.
```typescript
const { data } = useQuery('recentFeeds', getRecentFeeds, {
  onError: (error) => {
    console.log(error);
  },
});
```
이 onError안에 에러 코드에따른 에러 핸들링을 하면 된다.
이렇게하면 에러 핸들링하는 로직또한 분리가 가능해서 더 좋다고 판단하여 `try, catch`가 아닌 이방법을 쓰려한다.
에러 바운더리 라는 것도 있는데 아직 이해가 부족해서 더 살펴봐야겠다.

# 📌to do
- 그룹 생성 모달 로직 계속



</details>

