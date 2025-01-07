# SHOP TABLE RESERVATION SERVICE
2024 매장 테이블 예약 서비스

# < 개발 중 문의사항 >
1. 테이블 pk 자동증가 이유
   > 강의에서 DB 테이블 생성 시 id값을 자동증가하도록 설정합니다.   
   현재 프로젝트에서에서 리뷰 정보를 담는 테이블의 경우 pk값으로 예약번호(id)와 순번을 복합키로 이용해도 될 것 같은 생각이 듭니다.   
   복합키로 pk를 만들 수도 있는데 모든 id를 자동증가로 생성하는 이유가 있을까요?
   DB 설계시 id를 테이블의 pk로 사용하는 것이 권장되는 벙법인지 궁금합니다.


2. 테이블의 pk 자동 증가 시 createId, updateId 저장불가
   > 회원가입 시 테이블의 PK값인 id가 자동증가하도록 했습니다.   
   id값이 자동증가하다보니 insert 하기 전에 생성자, 수정자 id를 추가해 저장할 수 없습니다.   
   @CreatedBy를 통해 insert 시 id값이 자동 입력되게 하고싶은데 이 또한 결국 id값을 알고있어야 가능한 것 같습니다.
   회원가입 시, 즉 회원가입 전이라 사용자의 id가 없는 경우에는 데이터를 넣을 수 없나요?
   > - 참고 : SignUpService.saveManager()

3. exception handler 2개 생성 시 오류발생
   > 초기에 사용자정의 공통에러를 생성하는 CommonExceptionHandler를 작성했습니다.   
   그리고 스프링 시큐리티 관련 핸들러인 CustomAccessDeniedHandler, CustomAuthenticationEntryPoint 예외처리를 위해
   SpringSecurityExceptionHandler를 작성했습니다.   
   추후 발생하는 다른 모든 에러에 대해 예외처리를 수행하기위해 Exception 전체를 인자로 받는
   CommonExceptionHandler.defaultHandler()를 생성했습니다.   
   그랬더니 이전에 잘 동작했던 SpringSecurityExceptionHandler에 작성된 예외처리가 정상수행되지 않았습니다.   
   정의한 default예외 발생시키거나 인증관련 예외를 발생시켜야하는데 인증허가되는 오류가 발생하기 시작했습니다.   
   정의한 인증예외를 던지지 않고 정의한 default예외(CommonExceptionHandler.defaultHandler())를 발생시키는 오류로 인해 
   두 exception handler를 통합했습니다.   
   SecurityExceptionHandler와 CommonExceptionHandler를 통합하니까 해당 오류가 발생하지 않는 것 같아 수정은 했지만 어떤 이유에서 그렇게 되는지 궁금합니다.
   > - 참고 : CommonExceptionHandler.java

4. JPA 긴 쿼리 관리하기
    > 쿼리를 직접 작성할 때  @Query의 value속성에 작성하지 않고 파일로 빼서 쿼리를 관리할 수 있는 방법이 있는지 궁금합니다.    
      아니면 보통 쿼리가 길어지더라도 @Query에 value 속성에 작성하나요?
    > - 참고 : ShopRepository.java

    

# 개요
    간단한 매장 테이블 예약 서비스 제공 프로젝트 

    Goal : 매장 이용을 위한 방문 예약 가능한 예약 서버를 구축한다.

    Use : SpringBoot, Jpa
          Java 17
          Database - RDB : Mysql
          Test - Junit5
          Build - Gradle
          Login Token - JWT
          Test UI = PostMan
    
    산출물 : DB ERD, 

## 1. 매장 예약 서비스를 위한 기본구현기능.
    - 필요 테이블 : 회원테이블(매장이용자,관리자 동시관리), 권한테이블, 매장테이블

### 공통
- [x] 로그인 토큰 발생 (003-SIGN-IN)
- [x] 로그인 및 권한체크 (003-SIGN-IN)
- [x] 로그인 토큰을 이용한 제어 확인 (003-SIGN-IN)
    - (로그인한 상태에서만 페이지에 접근가능 \ JWT, Filter 이용해 간략하게 진행)

### 고객(매장이용자)
- [x] 회원 가입 (002-SIGN-UP-ALL)
- [x] 매장 검색 (007-SHOP-LIST-SEARCH)
    - 가나다순, 별점순, 거리순으로 정렬가능.  
- [ ] 매장상세정보 확인
- [ ] 매장 예약
    - 예약 가능여부 확인. 
    - 매장 예약은 매장상세정보 페이지에서 가능.
    - 예약 진행을 위해 회원가입 필수.
    - 예약 시 필요정보 : 상점, 날짜, 시간, 전화번호
- [ ] 도착 확인 
    - 예약 이후 예약시간 10분 전 키오스크를 통한 방문확인.
    - 예약 10분 전 방문확인 하지 않은 경우 예약 취소.
- [ ] 예약 및 사용 이후 리뷰 작성
    - [ ] 리뷰 작성 : 예약자만 작성 가능. 
    - [ ] 리뷰 수정 : 리뷰 작성자만 가능.
    - [ ] 리뷰 삭제 : 리뷰 작성자, 매장관리자만 가능.
    
### 점장(매장관리자)
- [x] 파트너(회원) 가입 (001-SIGN-UP)
- [x] 매장 등록 (004-SHOP-REGISTER)
    - 파트너 회원가입 후 등록가능. (스프링 시큐리티에서 인증 절차 거치므로 service단에서 체크하지않음.)
    - 승인조건없음.
- [x] 매장 수정 (005-SHOP-MODIFY)
- [x] 매장 삭제 (006-SHOP-REMOVE)
- [ ] 예약 정보 확인 
    - 날짜별 시간 테이블 목록 확인가능.


---
## 2. 매장 예약 서비스 고도화.

### 점장(매장관리자)
- [ ] 예약에 대한 승인/거절 기능.
    - 예약 후 바로 해당 시간에 매장을 이용하는 것은 매장의 상황을 고려하지 않고 진행됨.
      매장 상황을 고려해 운영하기 위해 점장이 들어온 예약에 대해 승인, 거절 후 예약 확정되도록 진행.



