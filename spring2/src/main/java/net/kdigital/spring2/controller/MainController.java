package net.kdigital.spring2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j // lombok의 log를 찍어주는 annotation
public class MainController {
    
    @GetMapping({"/", ""}) // 메소드 구동 요청이 하나면 {}필요없음
    public String index() {
        log.info("도착!");
        
        return "index";
    }
}
