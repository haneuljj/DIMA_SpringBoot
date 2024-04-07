package net.kdigital.spring7.handler;

import java.io.IOException;
import java.net.URLEncoder;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    // Controller가 아니기때문에 응답 객체 필요
    // 응답객체와 요청객체 함께
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        log.info("로그인 실패 {}", exception.getClass());
        
        String errMessage = "";
        
        if(exception instanceof BadCredentialsException) {
            errMessage = exception.getMessage();
            errMessage = "\n아이디나 비밀번호가 잘못 되었습니다.";
        } else {
            errMessage = exception.getMessage();
            errMessage += "\n로그인에 실패하였습니다. 관리자에게 문의하세요";
        }
        errMessage = URLEncoder.encode(errMessage, "UTF-8");   
        
        // 로그인 실패시 갈 url경로, 파라메터로 error,errMessage 보내기
        setDefaultFailureUrl("/user/login?error=true&errMessage="+errMessage);
        
        super.onAuthenticationFailure(request, response, exception);
    }
    
    }
