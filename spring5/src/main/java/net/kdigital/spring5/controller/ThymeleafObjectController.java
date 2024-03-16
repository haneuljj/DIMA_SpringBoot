package net.kdigital.spring5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/display")
@Slf4j
// 하나의 클래스 안에 여러 개의 메소드가 매핑되고 요청방식에 공통적인 데이터가 있다면,
// 클래스 위에서 공통 부분을 빼서 쓸 수 있음
public class ThymeleafObjectController {

    @GetMapping("/object")
    public String object(Model model) {

        String korean = "동해물과 백두산이 마르고 닳도록 하느님이 보우하사 우리나라 만세";
        String english = "I have a dream, a song to sing. To help me cope with anything";

        model.addAttribute("korean", korean);
        model.addAttribute("english", english);
        
        return "thyme_object";
    }
}
