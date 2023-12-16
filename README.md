# ✈️ TravelMaker
<img src="https://github.com/travelMaker7/travelMaker_Backend/assets/94064359/3e8481e3-7b71-42de-9e0d-795627d366e5" width="200px" height="200px">


## 프로젝트 개요
본 프로젝트는 <b>`여행 동행 서비스`</b>입니다. <br>
해당 서비스의 주된 대상은 `특정 여행지의 동행을 원하는 사용자` 입니다.<br>
- 여행을 하고 싶지만 친구들과의 시간조율이 어려운 경우! 
- 친구와의 여행 취향이 달라 의견 충돌을 겪은 경우!
- 혼자 여행을 즐기지만 가끔은 누군가와 함께하는 동행을 구하고 싶은 경우!
<p>해당 서비스를 이용함으로써 해소할 수 있습니다</p>

### travelMaker의 차별점
동행을 구하는 커뮤니티 또는 동행을 구하는 다른 비슷한 사이트는 존재합니다. 하지만 해당 사이트에서 실제로 동행이 이루어 지는 경우는 많지 않습니다. <br>
<p>왜 그럴까요? 이유는 여행일정 전체를 함께하는 부담이 존재하기 때문입니다. 또한 불분명한 상대와의 동행은 신뢰가 없기 때문에 불안감도 존재합니다.</p>
<p> travelMaker는 해당 문제를 해결하기 위해 다음과 같은 차별점을 가지고 있습니다. </p>

- 여행일정을 등록하는 유저는 동행을 원하는 여행지에는 희망동행을 open해 둡니다.<br>
  예비 동행자는 구체적인 여행지를 선택할 수 있고, 특정 여행지 기준으로 동행 매칭이 이루어지므로 <b>`동행 매칭률의 상승`</b>을 기대할 수 있습니다.
- 프로필 페이지에서 리뷰를 확인함으로써 신뢰도를 파악할 수 있습니다.

### 프로젝트 기간
<p>2023.10.16 ~ 2023.12. (현재 고도화 작업 진행중)</p>
상세 일정<br>
10/16 ~ 10/20 프로젝트 기획 및 개발환경 세팅<br>
10/23 ~ 10/30 1차 MVP 기능 구현<br>
10/30 ~ 11/1 ERD 엔티티 추가 및 수정<br>
11/2 ~ 11/22 2차 MVP 기능 구현<br>
11/23 ~ 11/28 배포 및 테스트<br>
11/29 ~ 12/5 테스트 후 수정 및 기능 보완

## TravelMaker 기술 스택
### 프론트엔드
<img width="289" alt="프론트 기술스택" src="https://github.com/travelMaker7/travelMaker_Backend/assets/94064359/1f560223-a9ae-4ec5-9c5e-b7b1269d3b78">

### 백엔드
<img width="482" alt="백엔드 기술스택" src="https://github.com/travelMaker7/travelMaker_Backend/assets/94064359/c3b59d94-e9e9-4eb7-babf-2b4d310ef18a">

### 협업툴
<img width="352" alt="협업툴" src="https://github.com/travelMaker7/travelMaker_Backend/assets/94064359/6f31dfa4-07fd-4c92-bb8a-b0b169637089">


## TravelMaker ERD 구조
<img width="906" alt="image" src="https://github.com/travelMaker7/travelMaker_Backend/assets/94064359/aa79ed18-ebc3-4729-b60a-1f990c2e0276">

## 인프라 구조
<img width="975" alt="서비스구조" src="https://github.com/travelMaker7/travelMaker_Backend/assets/94064359/76a47773-8e56-4f76-b9c0-a3d0e9026662">

## 백엔드 CI/CD
<img width="622" alt="CI:CD" src="https://github.com/travelMaker7/travelMaker_Backend/assets/94064359/7341a7f8-263b-4af9-8376-6b759b57ae55">   

## 프론트엔드 CI/CD
<img width="574" alt="프론트 CI:CD" src="https://github.com/travelMaker7/travelMaker_Backend/assets/94064359/4c2666c5-c19a-4da9-b3b1-bb61519432c0">


## 기능 소개

#### 1. 카카오 소셜 로그인
사용자는 번거로운 회원가입 절차를 하지 않고 카카오 소셜로그인으로 회원가입 및 로그인을 수행할 수 있습니다

#### 2. 일정등록
여행을 계획하는 사용자는 동행을 구하는 여행지만 콕 집어 동행 희망여부와 희망인원을 지정할 수 있습니다

#### 3. 동행신청 알림
일정을 등록한 사용자는 동행신청이 올 경우 프로필 페이지에서 확인 가능합니다
이는 리액트쿼리의 polling방식을 사용해 5초에 한번씩 변경사항이 반영되도록 구현하였습니다
  
#### 4. 1:1 채팅
예비 동행자는 일정을 등록한 사람의 프로필에서 1:1채팅 버튼을 통해 이야기를 주고 받을 수 있습니다<br>
websocket을 사용해 실시간채팅이 가능하고 stomp방식으로 메시지 형식을 정의해 서버와 클라이언트 간 일관된 통신이 가능합니다<br>

#### 4. 리뷰
함께 동행을 한 유저들이 칭찬배지를 부여하여 리뷰를 등록할 수 있습니다
칭찬배지는 `친절해요`, `시간은 금`, `포토그래퍼`, `여행가이드 뺨침`으로 구성해 비유적으로 표현하였습니다
  
#### 5. 검색
원하는 지역, 여행지, 날짜, 희망 성별, 희망 연령층을 검색할 수 있습니다


## 팀원 소개
### 프론트엔드
|<img src="https://github.com/travelMaker7/travelMaker_Backend/assets/94064359/cbc4c9e9-9cda-4f15-a3d2-a4d90457db33" width="30px" height="30px">|<img src="https://github.com/travelMaker7/travelMaker_Backend/assets/94064359/cbc4c9e9-9cda-4f15-a3d2-a4d90457db33" width="30px" height="30px">|<img src="https://github.com/travelMaker7/travelMaker_Backend/assets/94064359/cbc4c9e9-9cda-4f15-a3d2-a4d90457db33" width="30px" height="30px">|
|---|---|---|
|김윤동|김형준|이수민|

### 백엔드
|<img src="https://github.com/travelMaker7/travelMaker_Backend/assets/94064359/cbc4c9e9-9cda-4f15-a3d2-a4d90457db33" width="30px" height="30px">|<img src="https://github.com/travelMaker7/travelMaker_Backend/assets/94064359/cbc4c9e9-9cda-4f15-a3d2-a4d90457db33" width="30px" height="30px">|<img src="https://github.com/travelMaker7/travelMaker_Backend/assets/94064359/cbc4c9e9-9cda-4f15-a3d2-a4d90457db33" width="30px" height="30px">|
|---|---|---|
|고혁진|김지수|박경선|

