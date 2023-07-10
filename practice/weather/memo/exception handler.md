# Exception Handler
### Exception Handler란?
- @Controller, @RestController 의 예외를 하나의 메소드에서 처리해주는 기능

- @ExceptionHandler()
  - 컨트롤러안에서 일어나는 모든류의 상황을 디텍팅해서 처리한다.
  - @ExceptionHandler(~.class)
    - 컨트롤러안에서 일어나는 예외중에 ~.class 타입에만 처리하도록 설정 
  - @ExceptionHandler()쓰면 컨트롤러 전체에서 생기는 에러들을 처리할 수있다는 장점이 있다. 하지만 컨트롤러가 많아진다면 각각의 컨트롤러에 다 지정을 해주어야한다. 즉 비슷한 예외처리나 수정해야할 코드가 컨트롤러의 개수만큼 많아진다.

- @ControllerAdvice
  - 모든 Controller단을 대상으로 하여 예외가 발생한 것을 잡아줌
