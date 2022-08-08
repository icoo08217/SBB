package com.ll.exam.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @GetMapping("/sbb")
    @ResponseBody // 아래 함수의 리턴값을 그대로 브라우저에 표시함.
    // 아래 함수의 리턴값을 문자열화 해서 부라우저 응답의 바디에 담는다.
    public String index() {
        System.out.println("Hello"); // 콘솔에 출력
        return "안녕하세요~~~~~~~!!!!!!!!!!"; // 먼 미래에 브라우저에서 보여짐
    }

    @ResponseBody
    @GetMapping("/sbb1")
    public String showMake() {
        return "<input type=\"text\">";
    }
}
