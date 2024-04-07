package net.kdigital.spring7.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
import net.kdigital.spring7.handler.CustomFailureHandler;

@Configuration      // 설정 파일임을 나타내는 Annotation
@EnableWebSecurity  // 웹 관련 보안설정이 이루어지는 파일임을 나타내는 Annotation
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomFailureHandler failureHandler;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 1) 웹에 들어오는 요청에 대한 접근 권한 설정 
        //    spring 3.2이상 방식 ==> 람다식 사용 
        http
            .authorizeHttpRequests((auth) -> auth
                .requestMatchers( 
                        "/"
                        ,"/board/boardList"
                        , "/board/boardDetail"
                        , "/user/join"
                        , "/user/joinProc"
                        , "/user/login"
                        , "/user/loginProc"
                        , "/user/userIdCheck"
                        , "/reply/replyAll"
                        , "/images/**"
                        , "/css/**"
                        , "/script/**").permitAll() // permitAll(): 로그인(인증)하지 않아도 접근 가능한 페이지들 설정
                .requestMatchers("/admin/**").hasRole("ADMIN") // 관리자만 접근 가능한 페이지
                .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER") // 사용자 및 관리자가 접근 가능한 페이지
                .anyRequest().authenticated() // 그 외 나머지 경로들은 인증된 사용자만 접근 가능(가장 마지막에 와야함)
                );
        
        // 2) Custom Login 설정
        http
            .formLogin((auth) -> auth
                .loginPage("/user/login") // 로그인 화면 요청
                .failureHandler(failureHandler) // 로그인 처리 중 에러 발생시 처리할 Handler객체 등록
                .usernameParameter("userId")
                .passwordParameter("userPwd")
                .loginProcessingUrl("/user/loginProc") // 로그인 처리할 경로
                .defaultSuccessUrl("/") // 로그인 성공 시 이동할 경로
                .permitAll() // 모든 사용자가 요청 가능함
                );

        // 3) Logout 설정
        http
            .logout((auth) -> auth
                .logoutUrl("/user/logout") // 로그아웃 처리할 경로
                .logoutSuccessUrl("/") // 로그아웃 성공 시 이동할 경로
                .invalidateHttpSession(true)  // http 세션 무효화
                .deleteCookies("JSESSIONID") // 쿠키 삭제 (로그아웃시 제거할 쿠키명) 
                );
        
        // 4) CSRF(Cross-Site Request Forgery) 비활성화 처리
        // CSRF: Security는 사이트 위변조 기능이 설정되어 있기 때문에  모든 POST 요청시 CSRF 토큰도 보내야함
        // 개발 중에는 활성화 시 처리할 작업이 많아지므로 잠시 비활성화 시킴 (POST, PUT, DELETE요청이 진행 안됨)
        // 배포시에는 반드시 활성화시켜야함!!!
        http
            .csrf((auth) -> auth.disable());

        return http.build();
    }

    // 비밀번호 암호화 작업
    // Bean: 스프링 컨테이너가 관리하는 객체
    @Bean 
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
