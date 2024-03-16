package net.kdigital.spring6.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kdigital.spring6.dto.FriendDTO;
import net.kdigital.spring6.service.FriendService;

@Controller
@Slf4j
@RequiredArgsConstructor // 생성자 주입 방식을 lombok으로 생성하기, final 키워드가 붙은 멤버를 초기화할 때 사용
public class FriendController {

    private final FriendService service; // DI: Spring이 생성한 걸 받아서 사용

    /*
     * 생성자 주입방식
     * - 전에는 @Autowired라는 annotation을 사용했었음
     * - = new()로 생성은 절대 불가, 스프링 컨테이너가 관리하는 라이프사이클을 무시하고 만드는 것임
     * - 아래의 코드처럼 생성자 주입방식 사용 권고
     */
    // public FriendController(FriendService service) {
    // this.service = service;
    // }

    @GetMapping("/insert")
    // 에러가 포함된 채로 인서트폼으로 이동하는 뷰단과 에러가 포함되지 않은 채로 이동하는 뷰단을 함께 사용하기 위해 아무것도 없는 모델을
    // 넣어줘야함
    public String insert(Model model) {
        model.addAttribute("friendDTO", new FriendDTO());
        return "insertForm"; // forwarding 방식, 에러 없이 가는 경우
    }

    @PostMapping("/insert")
    public String insert(
            // @Valid: friendDTO가 넘어올 때 검증할 것임을 표시
            // BindingResult: 오류메시지 담는 객체
            @Valid @ModelAttribute FriendDTO friendDTO, BindingResult bindingResult) {

        log.info("{}", friendDTO.toString());
        log.info("BindingReulst: {}", bindingResult); // 오류 확인

        // bindingResult 객체에 에러가 포함되면
        if (bindingResult.hasErrors()) {
            log.info("친구 정보 등록실패(오류 포함)");
            return "insertForm"; // 에러가 포함된 상태로 입력화면으로 보내기
        }
        service.insertFriend(friendDTO);

        // 입력하는 화면으로 이동 -> 위의 매핑 메소드, 입력화면을 요청하는 것을 다시 하도록
        return "redirect:/"; // 브라우저가 /insert를 다시 요청하도록 redirect 방식
    }

    @GetMapping("/list")
    public String list(Model model) {

        List<FriendDTO> friendList = service.selectAll();

        model.addAttribute("list", friendList);
        return "list";
    }

    @GetMapping("/deleteOne")
    public String deleteOne(
            @RequestParam(name = "friendSeq", defaultValue = "0") Long friendSeq) {

        log.info("삭제할 seq: {}", friendSeq);
        service.deleteOne(friendSeq);

        return "redirect:/list"; // 다시 전체 조회하는 코드 쓸 필요 없이 전체 데이터 조회하는 요청 다시함
    }

    @GetMapping("/updateOne")
    public String updateOne(
            @RequestParam(name = "friendSeq", defaultValue = "0") Long friendSeq,
            Model model) {

        log.info("수정할 seq: {}", friendSeq);
        FriendDTO friendDTO = service.selectOne(friendSeq);

        log.info("수정할 데이터: {}", friendDTO.toString());

        model.addAttribute("friendDTO", friendDTO);

        return "updateForm";
    }

    @PostMapping("/updateProc")
    public String updateProc(
            @Valid @ModelAttribute FriendDTO friendDTO, BindingResult bindingResult) {

        log.info("{}", friendDTO.toString());
        log.info("bindingresult: {}", bindingResult);

        if (bindingResult.hasErrors()) {
            log.info("친구 정보 수정 실패 (오류 발견)");
            return "updateForm";
        }

        service.updateProc(friendDTO);

        return "redirect:/list";
    }
}
