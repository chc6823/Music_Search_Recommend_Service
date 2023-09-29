# playdata_miniproject1

플레이데이터 백엔드 부트캠프 미니프로젝트1

### 주제 : 음원 목록 출력 및 추천 서비스

- 프로젝트 수행 기간 : 5일
- 1인 프로젝트
- JDBC 기반 프로젝트
- 이 프로젝트는 초보적인 MVC 모델을 따르고 있음.
- 공공데이터포털에서 찾은 (재)한국저작권보호원_음악 저작물 API 목록 텍스트 파일에는 노래 제목, 가수,앨범,유통사등의 데이터를 활용함.
- (재)한국저작권보호원_음악 저작물 API 목록 텍스트 파일을 파싱하여 HTML 파일로 출력함
- 검색 기능을 제공함
- 추천 알고리즘
    - 나를 포함해 100명의 음원 평가 데이터를 1차원 행렬,즉 벡터로 생성함
    - 사용자1 - (사용자 1이 평가한 노래 1 평점,사용자 1이 평가한 노래 2 평점…사용자 1이 평가한 노래 100 평점)
    - 이 벡터들의 코사인 유사도를 계산한 후, 나와 가장 유사한 사용자가 높게 평가한 노래를 무작위로 추천해줌.

### 프로젝트 구조

1. 데이터베이스
- music 테이블 -

create table music(
song_id int(10) auto_increment,
song varchar(100) not null,
album varchar(100),
artist varchar(100),
music_distribution_company varchar(100),
company varchar(100),
PRIMARY KEY(song_id));

- user 테이블 - 서비스 사용자 테이블.user_id로 구분

create table User(
user_id int(10) auto_increment PRIMARY KEY);

- User_rating 테이블 - 유저 평점 테이블. 유저(user_id )가 특정 노래(song_id)를 몇 점(rating)으로 평가했는지 저장

create table User_rating(
rating_id int(3) auto_increment primary key,
user_id int(10),
song_id int(10),
rating float(1),
foreign key(user_id) references User(user_id),
foreign key(song_id) references music(song_id));

1. 주요 클래스 

- Main Class - Controller역할 클래스.  switch문으로 메뉴를 구성하였음.
- HTML Class - 출력 담당(뷰)
- DB Class - 비즈니스 로직(DB 접속,추천 기능) 담당
- Song Data Class - 음원 정보 DTO
