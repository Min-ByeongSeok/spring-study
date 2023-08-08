package hello.servlet.web.frontController.v3;

import hello.servlet.web.frontController.ModelView;
import hello.servlet.web.frontController.MyView;
import hello.servlet.web.frontController.v2.ControllerV2;
import hello.servlet.web.frontController.v2.controller.MemberFormControllerV2;
import hello.servlet.web.frontController.v2.controller.MemberListControllerV2;
import hello.servlet.web.frontController.v2.controller.MemberSaveControllerV2;
import hello.servlet.web.frontController.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontController.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontController.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// url을 입력했을 때 /front-controller/v3/*의 경로라면 가장먼저 열리는 서블릿
@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 요청 URL를 가져옴
        String requestURI = request.getRequestURI();
        // 요청 URL에 맞는 ContollerMap의 밸류값을 가져온다.
        ControllerV3 controller = controllerMap.get(requestURI);

        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

            return;
        }

        // HttpServletRequest에 있는 파라미터를 전부 추출해서 Map타입으로 만든다.
        Map<String, String> paramMap = createParamMap(request);
        // request로 만든 paramMap으로 모델뷰를 만든다.
        ModelView mv = controller.process(paramMap);

        // controller.process(paramMap)으로부터 받은 모델뷰의 이름으로 뷰 리졸버를 호출한다.
        String viewName = mv.getViewName();
        // 뷰이름을 받은 뷰 리졸버는 url을 완성시켜서 MyView객체를 반환한다.
        MyView view = viewResolver(viewName);

        // 모델뷰의 밸류값, request, response 값들을 가지고 렌더링을 한다.
        view.render(mv.getModel(), request, response);
    }

    private static MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
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
