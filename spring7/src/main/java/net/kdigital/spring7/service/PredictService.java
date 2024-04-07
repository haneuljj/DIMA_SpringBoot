package net.kdigital.spring7.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.standard.Media;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kdigital.spring7.dto.Iris;

@Service
@RequiredArgsConstructor
@Slf4j
public class PredictService {

    @Value("${iris.predict.server}")
    String url;

    // RestTemplate API를 이용해서 동기통신, Component로 등록해서 호출하여 사용(AppConfig.java)
    private final RestTemplate restTemplate;

    public Map<String, String> predictRest(Iris iris) {
        // 에러 담을 Map
        Map<String, String> error = new HashMap<>();
        // 정상 결과 담을 Map
        Map<String, String> result = new HashMap<>();;

        try{
            // header 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            // 받을 데이터 타입 설정 - JSON
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            // (url, 보낼 데이터, 받을 데이터 타입), raw 타입의 Map
            ResponseEntity<Map> response = restTemplate.postForEntity(url, iris, Map.class);
            // 실제 데이터 -> Map의 body에 있음
            result = response.getBody();
        } catch(Exception e) {
            error.put("statusCode", "450"); // 사용자 지정 에러 코드, 사용자 지정 안해도 됨
            error.put("body", "오류발생");

            return error;
        }
        return result;
    }
    

}
