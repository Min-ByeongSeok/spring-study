# 실전 배당금 프로젝트

### 요구사항 정리

- <u>내가 원하는 회사</u>의 <u>배당금 정보</u>를 보고싶어요
    - [내]가 원하는
        - 회원가입 및 로그인/로그아웃 api
        - 사용자 인

    - [회사]를 저장/삭제 관리할 수 있는 기능
        - 회사 정보를 저장하는 api
        - 저장된 회사정보를 삭제하는 api
        - 회사 정보를 관리하기 위한 DB 설계

    - 회사에 해당하는 [배당금] 정보를 제공하는 기능
        - 회사의 배당금을 조회하는 api
        - 회사의 배당금 정보를 스크래핑
        - 회사 배당금 정보를 관리하기 위한 DB테이블 설계

- 정리
    - 스크래핑으로 배당금 데이터 가져오기
    - 서비스 제공을 위한 api 개발
    - 서버 부하를 줄이기 위한 캐시서버 구성하