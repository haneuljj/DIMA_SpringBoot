package net.kdigital.spring4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;
import net.kdigital.spring4.dto.FriendDTO;

@Controller
@Slf4j
public class FriendController {
    
    @PostMapping({"/regist"})
    public String regist(
        @ModelAttribute FriendDTO friendDTO, // 여러 파라메터를 담은 friendDTO로 데이터 받기
        Model model
    ) {
        log.info("{}", friendDTO.toString()); // toString()을 넣어서 문자열로 변환되어 로그 출력가능

        model.addAttribute("friend", friendDTO);  //("클라이언트가 뽑아서 쓸 데이터 이름", 보낼 데이터)

        return "registResult"; // forwarding 응답방식: Model에 데이터를 실어서 보냄
    }
}
