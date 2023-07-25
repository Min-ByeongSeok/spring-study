package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HelloController {
    @GetMapping("/hello")
    public String hello(Model model) {
        // model에 data라는 이름으로 hello!값을 가지는 데이터를 심어서 뷰로 넘긴다.
        model.addAttribute("data", "hello!");
        // 반환값은 뷰 네임
        return "hello";
    }
}
