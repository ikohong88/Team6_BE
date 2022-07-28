# Team6_BE (프로 트러블 슈터)
## 📅 프로젝트 기간 (2022.07.22 ~ 2022.07.28)
## 1. 프로젝트 소개
> 프로젝트 진행중에 발생한 에러사항을 게시판에 작성하고, 댓글작성을 통해 해결할수 있도록 도움을 주는 커뮤니티 게시판

## 2. API 설계
![회원가입 API](https://user-images.githubusercontent.com/85264446/180937754-70ea2a58-5193-4723-9f39-77f0884d9e0f.PNG)
![게시글1 API](https://user-images.githubusercontent.com/85264446/180937950-b135004b-484d-4c9e-bf04-84464326600d.PNG)
![게시글2 API](https://user-images.githubusercontent.com/85264446/180937981-407ff0a7-25d9-4975-906c-3fd8fb81a5f9.PNG)
![댓글 API](https://user-images.githubusercontent.com/85264446/180938009-0c6fed4a-5fe6-4a5d-977b-5e7755a8a8eb.PNG)

## 3. 와이어 프레임
> - 메인 화면
![인덱스페이지](https://user-images.githubusercontent.com/85264446/180930660-17bd1f63-2f34-426e-8468-19e56239b1b2.PNG)

> - 로그인 페이지
![로그인페이지](https://user-images.githubusercontent.com/85264446/180931007-54e81c1e-eea5-4269-a6c0-a85c6934413d.PNG)

> - 회원가입 페이지
![회원가입](https://user-images.githubusercontent.com/85264446/180931268-911d2c1f-a176-4beb-b748-fab7081687b8.PNG)

> - 게시글 작성
![글쓰기 페이지](https://user-images.githubusercontent.com/85264446/180931379-dd0271a9-dcd6-4368-ab7d-b8f43c79f19f.PNG)

> - 게시글 불러오기
![게시글 불러오기](https://user-images.githubusercontent.com/85264446/180931440-de7f9b87-eacf-43ec-a902-3df329d39fdf.PNG)

> - 게시글 수정
![게시글 수정하기](https://user-images.githubusercontent.com/85264446/180931553-6e82cc15-4839-4f73-94a9-6287659c1486.PNG)

> - 댓글 작성
![댓글 작성](https://user-images.githubusercontent.com/85264446/180931607-5f717e8c-bd8b-41b0-90cf-56c6dae6ff41.PNG)

> - 댓글 수정
![댓글 수정하기](https://user-images.githubusercontent.com/85264446/180932919-28c49519-f42d-4595-97aa-60fa389aa6fa.PNG)

## 4. FE github repo 주소
> * https://github.com/doa12/Team6_FE

## 5. 시연 영상

## 6. 개발툴
> * 서버 : AWS EC2 (우분투 18.04 LTS)
> * 데이터베이스 : MySQL
> * 활용언어 : Java
> * 에디터 : IntelliJ IDEA

## 7. 기능
* 로그인
* 회원가입
----------
* 게시글 조회
* 게시글 작성
* 게시글 수정
* 게시글 삭제
----------
* 댓글 작성
* 댓글 수정
* 댓글 삭제

## 8. 에러 & 해결
* AWS 서버 배포시 JWT관련 @Bean 생성 불가로 인한 스프링 구동 실패
 * compileOnly 'com.auth0:java-jwt:3.13.0' 를 implementation 'com.auth0:java-jwt:3.13.0'로 수정이후 에러현상 해결
* 로컬에서 CORS 테스트를 진행했을때, 'blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource.'에러 발생
 * .allowCredentials(true) 주석 처리 이후, 정상적으로 작동 [ 현재 프로젝트에서는 정상작동을 하나, 클라이언트에서 쿠키를 받을수 있도록 허용하는 코드라 수정이 필요할 수 있습니다. ]
* User 이름으로 테이블 생성시 에러가 발생하는 현상
 * 스프링 버전 2.7.2버젼에서 2.6.10버젼으로 다운그레이드 후 정상적으로 생성, 버젼 다운그레이드 하기전에는 @Table(name="UserTable")로 변경하여서 사용
* 통합 테스트 진행중, 'Ensure CORS response header values are valid' 에러 발생
 * CORS 설정에 .allowedHeaders(*).allowedMethods(*) 코드 추가 이후 해결
