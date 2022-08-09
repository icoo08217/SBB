package com.ll.exam.sbb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
    public String saveSession(@PathVariable String name, @PathVariable String value, HttpServletRequest req) {
        HttpSession session = req.getSession();

        // req => 쿠키 => JSESSIONID => 세션을 얻을 수 있다.

        session.setAttribute(name, value);

        return "세션변수 %s의 값이 %s(으)로 설정되었습니다.".formatted(name, value);
    }

    @GetMapping("/getSession/{name}")
    @ResponseBody
    public String getSession(@PathVariable String name, HttpSession session) {
        String value = (String) session.getAttribute(name);

        return "세션변수 %s의 값은 %s입니다.".formatted(name, value);
    }

    // 테스트 전용 리스트인 article
    private List<Article> articles = new ArrayList<>(
            Arrays.asList(
                    new Article("제목1", "내용1"),
                    new Article("제목2", "내용2")
            )
    );

    @GetMapping("/addArticle")
    @ResponseBody
    public String addArticle(@RequestParam String title,
                             @RequestParam String body) {
        Article article = new Article(title, body);
        articles.add(article);

        return "%d번 글이 등록되었습니다.".formatted(article.getId());
    }

    @GetMapping("/article/{id}")
    @ResponseBody
    public Article getArticle(@PathVariable int id) {
        Article article = articles
                .stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null);

        return article;
    }

    // http://localhost:8080/modifyArticle?id=1&title=제목 new&body=내용 new
    @GetMapping("/modifyArticle/{id}")
    @ResponseBody
    public String modifyArticle(@PathVariable int id,
                                @RequestParam String title,
                                @RequestParam String body) {
        Article article = articles
                .stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null);

        if (article == null) {
            return "%d번 게시물은 존재하지 않습니다.".formatted(id);
        }

        article.setTitle(title);
        article.setBody(body);

        return "%d번 글이 수정되었습니다.".formatted(article.getId());
    }

    @GetMapping("/deleteArticle/{id}")
    @ResponseBody
    public String modifyArticle(@PathVariable int id) {
        Article article = articles
                .stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null);

        if (article == null) {
            return "%d번 게시물은 존재하지 않습니다.".formatted(id);
        }

        articles.remove(article);

        return "%d번 글이 삭제되었습니다.".formatted(article.getId());
    }


    private List<Person> persons = new ArrayList<>(
            Arrays.asList()
    );
    @GetMapping("/addPerson")
    @ResponseBody
    public String addPerson(@ModelAttribute Person p){

        persons.add(p);

        return "%d번 고객이 등록되었습니다.".formatted(p.getId());
    }
}

@AllArgsConstructor
@Getter @Setter
class Article {
    private static int lastId=0;
    private int id;
    private String title;
    private String body;

    public Article(String title, String body) {
        this(++lastId, title, body);
    }
}

@AllArgsConstructor
@Getter @Setter
class Person {
    private int id;
    private int age;
    private String name;
}
