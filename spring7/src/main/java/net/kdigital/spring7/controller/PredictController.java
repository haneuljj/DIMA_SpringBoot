package net.kdigital.spring7.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kdigital.spring7.dto.Iris;
import net.kdigital.spring7.service.PredictService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PredictController {

    private final PredictService service;
    /**
     * 붓꽃 정보 예측하는 화면 요청
     * @return
     */
    @GetMapping("/predict")
    public String predict(){
        return "iris";
    }

    @PostMapping("/predict")
    @ResponseBody
    public Map<String, String> predict(
        @ModelAttribute Iris iris
    ) {
        Map<String, String> result = service.predictRest(iris);
        return result;
    }
}
