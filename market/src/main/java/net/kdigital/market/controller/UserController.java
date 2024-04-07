package net.kdigital.market.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kdigital.market.dto.MemberDTO;
import net.kdigital.market.service.MemberService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final MemberService memberService;

    /**
     * 회원 가입을 위한 화면 요청
     * @return
     */
    @GetMapping("/user/join")
    public String join(){
        return "user/join";
    }

    @PostMapping("/user/joinProc")
    public String joinProc(
        @ModelAttribute MemberDTO memberDTO
    ){
        log.info("============{}", memberDTO.toString());

        memberDTO.setRolename("ROLE_USER");
        memberDTO.setEnabled(true);

        memberService.joinProc(memberDTO);

        return "redirect:/";
    }

    @GetMapping("/user/login")
    public String login(
        @RequestParam(value = "error", required = false) String error
        , @RequestParam(value = "errMessage", required = false) String errMessage
        , Model model
    ) {

        model.addAttribute("error", error);
        model.addAttribute("errMessage", errMessage);

        return "user/login";
    }
    
}
