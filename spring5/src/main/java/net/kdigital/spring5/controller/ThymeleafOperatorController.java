package net.kdigital.spring5.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import net.kdigital.spring5.dto.FriendDTO;

@Controller
@RequestMapping("/display")
@Slf4j
public class ThymeleafOperatorController {

    @GetMapping("/operate")
    public String operate(Model model) {
        String name = "James Dean";
        String one = "한놈";
        String two = "두시기";
        String three = "석삼";

        int intNum = 22;
        double dbNum = 42.195;

        FriendDTO friend = new FriendDTO("마루치", 15, "010-1111-2222", LocalDate.of(2005, 3, 25), true);

        model.addAttribute("name", name);
        model.addAttribute("one", one);
        model.addAttribute("two", two);
        model.addAttribute("three", three);
        model.addAttribute("intNum", intNum);
        model.addAttribute("dbNum", dbNum);
        model.addAttribute("friend", friend);

        return "thyme_operate";
    }
}
