package hello.servlet.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("HelloServlet.service");
        // 출력결과 : HelloServlet.service
        System.out.println("request = " + request);
        // 출력결과 : request = org.apache.catalina.connector.RequestFacade@65524891
        System.out.println("response = " + response);
        // 출력결과 : response = org.apache.catalina.connector.ResponseFacade@2709d088

        // 쿼리 파라미터 확인하는법
        String username = request.getParameter("username");
        System.out.println("username = " + username);
        // 출력결과 : username = kim

        // 단순문자로 response(응답)으로 내보낸다.
        // http 헤더
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        // http 바디
        response.getWriter().write("hello " + username);
    }
}
