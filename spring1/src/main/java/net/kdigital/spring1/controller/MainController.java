package net.kdigital.spring1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // Spring Container의 관리 하에 들어감: 객체 생성, 객체 사용 후 소멸하는 객체의 라이프사이클 관리
public class MainController {
    /* 
     * 모든 프로젝트의 요청 경로는 동일하면 안됨!!!
     * 동일하게 할 경우 요청 방식이 달라야함!!!
     * @GetMapping("/")
     * @PostMapping("/") --> 요청방식이 달라서 가능
     * @GetMapping("/") --> 요청경로도 같고 요청방식도 같아서 불가능
     * 요청방식: @GetMapping, @PostMapping, @DeleteMapping, @PutMapping
     */
    @GetMapping({"", "/"}) // get방식으로 요청되었을 때; "": 아무것도 요청하지않을시, "/":/를 붙일시에도 실행
    public String index() {
        System.out.println("index()에 도착");

        return "index"; // src/main/resources/templete/index.html을 의미함(약속)
    };
}
