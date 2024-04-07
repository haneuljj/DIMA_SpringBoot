package net.kdigital.review.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kdigital.review.dto.ReviewDTO;
import net.kdigital.review.service.ReviewService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ReviewController {
    
    private final ReviewService reviewService;

    @PostMapping("/review/insertReview")
    @ResponseBody
    public ReviewDTO insertReview(
        @ModelAttribute ReviewDTO reviewDTO){
            log.info("======={}", reviewDTO);
            ReviewDTO saveResult = reviewService.insertReview(reviewDTO);

            return saveResult;
    }

    @GetMapping("/review/reviewAll")
    @ResponseBody
    public List<ReviewDTO> reviewAll(
        @RequestParam(name = "movieNum") Long movieNum
    ){
        List<ReviewDTO> reviewList = reviewService.reviewAll(movieNum);

        log.info("======reviewList: {}", reviewList);
        return reviewList;
    }

}
