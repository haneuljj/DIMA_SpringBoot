package net.kdigital.review.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kdigital.review.dto.MovieDTO;
import net.kdigital.review.entity.MovieEntity;
import net.kdigital.review.service.MovieService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final MovieService movieService;
    
    @GetMapping({"/", ""})
    public String index(
        //@RequestParam(name = "orderby", defaultValue = "movieDate") String criteria,
        @RequestParam(name = "searchGenre", defaultValue = "") String searchGenre, 
        Model model
    ){

        //log.info("======= orderby:{}", criteria);
        List<MovieDTO> dtoList = movieService.selectAll();

        model.addAttribute("list", dtoList);
        model.addAttribute("searchGenre", searchGenre);

        return "index";
    }

    @PostMapping("/getGenreList")
    @ResponseBody
    public List<MovieDTO> getGenre( 
        @RequestParam(name = "searchGenre", defaultValue = "") String searchGenre
    ){
        log.info("======= genre:{}", searchGenre);
        List<MovieDTO> dtoList = movieService.getGenreList(searchGenre);

        return dtoList;
    }

    @GetMapping("/sortList")
    public String sortList(
        @RequestParam(name = "orderby", defaultValue = "movieDate") String criteria
        , Model model
    ) {
        List<MovieDTO> dtoList = movieService.sortList(criteria);

        log.info("=============sort============");

        model.addAttribute("list", dtoList);

        return "index";
    }
        
}
