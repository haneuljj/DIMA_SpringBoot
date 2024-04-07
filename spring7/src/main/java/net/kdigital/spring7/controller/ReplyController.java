package net.kdigital.spring7.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kdigital.spring7.dto.ReplyDTO;
import net.kdigital.spring7.service.ReplyService;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/reply")
public class ReplyController {
    
    private final ReplyService replyService;

    @PostMapping("/replyInsert")
    @ResponseBody
    public ReplyDTO replyInsert(@ModelAttribute ReplyDTO replyDTO){

        log.info("{}", replyDTO);
        ReplyDTO saveResult = replyService.replyInsert(replyDTO);
        
        return saveResult;
    }

    @GetMapping("/replyAll")
    @ResponseBody
    public List<ReplyDTO> replyAll(@RequestParam(name = "boardNum") Long boardNum){

        List<ReplyDTO> replyList = replyService.replyAll(boardNum);

        log.info("=============> {}", replyList);

        return replyList;
    }

    @GetMapping("/replyDelete")
    @ResponseBody
    public boolean replyDelete(
        @RequestParam(name = "replyNum") Long replyNum){

        boolean result = replyService.replyDelete(replyNum);
        log.info("=============> {}", result);
        return result;
    }

    @GetMapping("/replyOne")
    @ResponseBody
    public ReplyDTO replyOne(
        @RequestParam(name = "replyNum") Long replyNum){

            ReplyDTO replyDTO = replyService.replyOne(replyNum);

            return replyDTO;
    }

    @GetMapping("/updateReply")
    @ResponseBody
    public ReplyDTO upadteReply(
        @RequestParam(name = "replyNum") Long replyNum
        , @RequestParam(name = "replyText") String replyText
    ){
        ReplyDTO replyDTO = replyService.updateReply(replyNum, replyText);

        return replyDTO;
    }
}
