package com.ll.exam.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class MainController {

    static int count = 0;

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

    @GetMapping("/plus")
    @ResponseBody
    public String plus(@RequestParam int a, @RequestParam int b) {
        return Integer.toString(a + b);
    }

    @GetMapping("/minus")
    @ResponseBody
    public String minus(@RequestParam int a, @RequestParam int b) {
        return Integer.toString(a - b);
    }

    @GetMapping("/increase")
    @ResponseBody
    public String increase() {
        return Integer.toString(count++);
    }

    @GetMapping("/gugudan")
    @ResponseBody
    public StringBuilder gugudan(@RequestParam int dan,
                                 @RequestParam int limit) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= limit; i++) {
            sb.append(dan).append(" * ").append(i).append(" = ").append(dan * i);
            sb.append('\n');
        }
        return sb;
    }

//    @GetMapping("/gugudan")
//    @ResponseBody
//    public String showGugudan(Integer dan, Integer limit) {
//        if (limit == null) {
//            limit = 9;
//        }
//
//        if (dan == null) {
//            dan = 9;
//        }
//
//        Integer finalDan = dan;
//        return IntStream.rangeClosed(1, limit)
//                .mapToObj(i -> "%d * %d = %d".formatted(finalDan, i, finalDan * i))
//                .collect(Collectors.joining("<br>\n"));
//    }

    @GetMapping("/mbti/{name}")
    @ResponseBody
    public String mbti(@PathVariable String name) {
        String rs = switch (name) {
            case "홍길동" -> "INFP";
            default -> "모름";
        };

        return rs;
    }

    @GetMapping("/saveSession/{name}/{value}")
    @ResponseBody
    public String saveSession(@PathVariable String name , @PathVariable String value, HttpServletRequest req){
        HttpSession session = req.getSession();

        // req => 쿠키 => JSESSIONID => 세션을 얻을 수 있다.

        session.setAttribute(name, value);

        return "세션변수 %s의 값이 %s(으)로 설정되었습니다.".formatted(name, value);
    }

    @GetMapping("/getSession/{name}")
    @ResponseBody
    public String getSession(@PathVariable String name , HttpSession session){
        String value = (String) session.getAttribute(name);

        return "세션변수 %s의 값은 %s입니다.".formatted(name, value);
    }
}
