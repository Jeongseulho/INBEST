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


</details>
