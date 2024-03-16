package net.kdigital.spring3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ParamController {
    
//    @GetMapping("/param/send")
//   public String param(String name, int age) { //(변수명)
//       log.info("전송받은 데이터: 이름 - {}, 나이 - {}", name, age);
//        return "result";
//    }

// parameter: 입력된 데이터를 담을 공간
// model: 보내는 데이터를 담을 공간

    @GetMapping({"/param/send", "/param/sendForm"}) // 하나의 get이 두개의 요청 받기
    public String param(
            @RequestParam(name="name", defaultValue="홍길동") String name, // (name=파라미터 이름, 클라이언트가 안보냈을 때 대체할 값) 
            @RequestParam(name="age", defaultValue="30") int age,
            Model model 
    ) { 
        log.info("전송받은 데이터: 이름 - {}, 나이 - {}", name, age);

        // key:value 쌍으로 보내야함
        model.addAttribute("name", name);
        model.addAttribute("age", age);

        return "result"; // forwarding 방식으로 응답, model에 담긴 데이터를 result에서 꺼내쓸 수 있도록(thymeleaf로)
        // 추가- redirect 방식
    }


}
