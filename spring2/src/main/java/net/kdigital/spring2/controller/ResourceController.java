// 이미지 요청 컨트롤러
package net.kdigital.spring2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResourceController {
    // 메소드 하나당 요청 하나
    @GetMapping("/image")
    public String image() {
        return "image";
    }

    @GetMapping("/css")
    public String css() {
        return "css";
    }

    @GetMapping("/javascript")
    public String javascript() {
        return "javascript";
    }
}
