# HTTP

### Hyper Text Transfer Protocol
- 단순 텍스트가 아닌 하이퍼 텍스트(다른 내용에 대한 링크를 갖는 문자열)를 전송하기 위한 프로토콜

#### HTTP Request 메시지 스펙
- 첫째 줄 : 요청라인 (HTTP 메소드(GET, PUT, POST 등))
- 두번째 줄 ~ 줄바꿈이 나오기 전까지 : Header(User-Agent, Accept 등)
- 헤더에서 줄바꿈 이후 : Request Body

```http request
POST /account HTTP/1.1 
// 요청라인
Content-Type: application/json //내가 전송할 타입
Accept: application/json // 응답으로 받고싶은 타입
UserInfo: {"userId":34} // 임의로 넣은 데이터

{
   "balance": 5000 // Requset의 바디 내용
}
```


#### HTTP Reponse 메시지 스펙
- 첫째 줄 : 상태라인 (200, 500 등)
- 두번째 줄 ~ 줄바꿈이 나오기 전까지 : Header
- 헤더에서 줄바꿈 이후 : Request Body

```http request
HTTP/1.1 200 OK
Content-Type: application/json
Transfer-Encoding: chunked
Date: Sat, 17 Jul 2021 HH:MM:SS GMT
Keep-Alive: timeout=60
Connection: keep-alive

{
    "developerLevel":"JUNIOR",
    "developerSkillType":"FULL_STACK",
    "experienceYears":"2",
    "memberId":"sunny.flo1wer",
    "name":"sun",
    "age": 36
}
```