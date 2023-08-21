### API 인터페이스 설계
- 각 api 메소드에 대한 정보/설명  
    ~~http://www.w3.org/Protocols/rfc2616/rfc2616-sec5.html#sec5.1.1~~
    
    https://www.rfc-editor.org/rfc/rfc9110.html


- 특정회사의 배당금 조회
  - 사용자가 해당 회사데이터에 접근을 할때 클라이언트앱에서 서버로 회사의 데이터를 요청한다.
  - 캐싱을 써서 클라이언트앱 자체에서 데이터를 저장할 수도 있다. 다만 더 많은 저장용량을 차지해서 트레이드오프가 발생한다.
  - GET
  - /finance/dividend/{companyName}
  - 응답 : JSON {companyName : "", dividend[{data: "", price: "", ...}, ...]}
    - 이때 배당의 정보는 단건이 아닌 여러 건의 정보가 될 수 있기 때문에 리스트형태의 응답을 띈다.


- 배당금 검색 - 자동완성
  - 배당금 검색을 하기위한 검색창(회사명 검색 or 티커 검색)
  - GET
  - /company/autocomplete?keyword=O
  - 응답 : JSON ["O", "OAS", ...]


- 회사 리스트 조회
  - GET
  - /company
  - 응답 : JSON {result : [{companyName: "", ticker: ""}, ...]}


- 관리자 기능 - 배당금 저장
  - 관리자를 위한 기능 (회사 추가/삭제 기능)
  - POST
  - /company
  - url에서 회사를 구분하기 위한 티커명을 받아서 티커명에 해당하는 회사데이터를 저장
    - body {ticker : ""}
  - JSON {ticker : "", companyName: "", ...}


- 관리자 기능 - 배당금 삭제
  - DELETE
  - /company?ticker=GOOD


- TODO : 회원 API
  - 회원가입
  - 로그인
  - 로그아웃

### DB 설계
- DB설계에 고려할 수 있는 것들
  - 어떤 타입의 데이터가 저장되는지
  - 데이터의 규모는 어떻게 되는지
  - 데이터의 저장 주기는 어떻게 되는지
  - 데이터의 읽기와 쓰기의 비율
  - 속도 vs 정확도
  - READ 연산시 어떤 컬럼을 기준으로 읽어오는지(인덱스)
  - 키는 어떻게 생성해줄건지
  - 예상 트래픽은 어느 정도인지
  - 파티션은 어떻게 구성할건지
  - 등등..