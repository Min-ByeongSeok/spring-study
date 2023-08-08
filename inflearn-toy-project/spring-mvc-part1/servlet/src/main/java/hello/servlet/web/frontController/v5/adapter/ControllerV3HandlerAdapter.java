package hello.servlet.web.frontController.v5.adapter;

import hello.servlet.web.frontController.ModelView;
import hello.servlet.web.frontController.v3.ControllerV3;
import hello.servlet.web.frontController.v5.MyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV3HandlerAdapter implements MyHandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        // ControllerV3의 구현체(ControllerV3를 처리할 수 있는 어댑터)이면 참
        return (handler instanceof ControllerV3);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        // supports 메서드로 ControllerV3인 것을 확인했기때문에 타입변환 가능
        ControllerV3 controller = (ControllerV3) handler;

        // request요청에대한 파라미터정보들을 Map형식으로 만듦
        Map<String, String> paramMap = createParamMap(request);
        ModelView mv = controller.process(paramMap);

        return mv;
    }

    private static Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();

        request.getParameterNames() // 모든 파라미터네임을 가져옴
                .asIterator()
                .forEachRemaining( // 모든 원소에대해 주어진 작업을 함
                        // paramName이라는 키 변수명과 키값에 맞는 밸류값을 추출함.
                        paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
