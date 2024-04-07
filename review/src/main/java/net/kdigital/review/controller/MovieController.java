package net.kdigital.review.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kdigital.review.dto.MovieDTO;
import net.kdigital.review.service.MovieService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;
    
    /**
     * 영화 정보 등록 작성 화면 요청
     * @return
     */
    @GetMapping("/movie/insertMovie")
    public String insertMovie() {
        return "writeMovie";
    }

    /**
     * 영화 정보 등록 처리
     * @param movieDTO
     * @return
     */
    @PostMapping("/movie/insertMovie")
    public String insertMovie(
        @ModelAttribute MovieDTO movieDTO
    ) {
        log.info("======== {}", movieDTO);

        movieService.insertMovie(movieDTO);

        return "redirect:/";
    }

    @GetMapping("/movie/movieDetail")
    public String movieDetail(
        @RequestParam(name = "movieNum") Long movieNum
        , Model model) {
            MovieDTO movieDTO = movieService.selectOne(movieNum);

        model.addAttribute("movie", movieDTO);

        return "movieDetail";
    }

    @GetMapping("/movie/updateMovie")
    public String updateMovie(
        @RequestParam(name = "movieNum") Long movieNum,
        Model model
    ){
        MovieDTO movieDTO = movieService.selectOne(movieNum);

        model.addAttribute("movie", movieDTO);

        return "updateMovie";
    }

    @PostMapping("/movie/updateMovie")
    public String updateMovie(
        @ModelAttribute MovieDTO movieDTO,
        RedirectAttributes rttr){
        movieService.updateMovie(movieDTO);

        log.info("======수정된 dto: {}", movieDTO.toString());

        rttr.addAttribute("movieNum", movieDTO.getMovieNum());

        return "redirect:movieDetail";
    }

    @GetMapping("/movie/deleteMovie")
    @ResponseBody
    public String deleteMovie(
        @RequestParam(name = "movieNum") Long movieNum
    ){
        movieService.deleteOne(movieNum);
        return "/";
    }
}
