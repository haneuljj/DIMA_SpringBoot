package net.kdigital.test2.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
import net.kdigital.test2.dto.GuestbookDTO;
import net.kdigital.test2.service.GuestbookService;

@Controller
@Slf4j
public class GuestbookController {
    private GuestbookService service;

    public GuestbookController(GuestbookService service) {
        this.service = service;
    }

    @GetMapping("/insert")
    public String insert() {
        return "insertForm";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute GuestbookDTO guestbookDTO) {
        log.info("{}", guestbookDTO.toString());
        service.insertGuestbook(guestbookDTO);

        return "redirect:/";
    }

    @GetMapping("/list")
    public String list(Model model) {

        List<GuestbookDTO> guestList = service.selectAll();

        model.addAttribute("list", guestList);

        return "list";
    }

    @GetMapping("/deleteOne")
    public String deleteOne(
            @RequestParam(name = "guestSeq", defaultValue = "0") Long guestSeq) {

        log.info("삭제할 DTO의 번호: {}", guestSeq);
        service.deleteOne(guestSeq);

        return "redirect:/list";
    }

    @GetMapping("/updateOne")
    public String updateOne(
            @RequestParam(name = "guestSeq", defaultValue = "0") Long guestSeq,
            Model model) {
        log.info("수정할 DTO의 번호: {}", guestSeq);
        GuestbookDTO guestbookDTO = service.selectOne(guestSeq);

        log.info("수정할 데이터: {}", guestbookDTO.toString());

        model.addAttribute("guestDTO", guestbookDTO);

        return "updateForm";
    }

    @PostMapping("/updateProc")
    public String updateProc(@ModelAttribute GuestbookDTO guestbookDTO) {
        log.info("수정할 내용: {}", guestbookDTO);

        service.updateProc(guestbookDTO);

        return "redirect:/list";
    }

}
