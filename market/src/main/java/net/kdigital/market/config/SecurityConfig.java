package net.kdigital.market.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 1) 웹요청에 대한 접근 권한 설정
        http
            .authorizeHttpRequests((auth) -> auth
                .requestMatchers(
                    "/"
                    , "/user/join"
                    , "/user/joinProc"
                    , "/user/login"
                    , "/user/loginProc"
                    , "/script/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN") // 관리자만 접근 가능한 페이지
                .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER") // 사용자 및 관리자가 접근 가능한 페이지
                .anyRequest().authenticated()
                );

        // 2) login 설정
        http
            .formLogin((auth) -> auth
                .loginPage("/user/login")
                .usernameParameter("memberId")
                .passwordParameter("memberPw")
                .loginProcessingUrl("/user/loginProc")
                .defaultSuccessUrl("/")
                .permitAll()
                );

        // 3) logout 설정
        http
            .logout((auth) -> auth
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            );

        // 4) CSRF 비활성화 처리
        http
            .csrf((auth) -> auth.disable());

        return http.build();
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
