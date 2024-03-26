### QueryDsL 공부하기

- 인프런 김영한님 강의를 통해서 배워보자.
- QueryDsl 과 JPQL 에대해서 알아보자.
- https://velog.io/@steady_aa/series/Querydsl


## 순수 JPA repo에 Querydsl 추가
- memberJPARepo 를 추가
  - Querydsl을 memberJpaRepo에 추가.
  - 동적 쿼리에서 적용하기 위한 Dto 추가
  - Builder를 이용한 동적쿼리 처리
  - BoolanExpression을 통해서 동적 처리

## Profile을 통해서 실전 데이터와 테스트 분리
- 실전 데이터는 profile이 local인 부분에서만 실행되게 추가  
  - 샘플 데이터 추가