package com.example.troubleshooter;

import com.auth0.jwt.JWT;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ServletComponentScan("lecturer")
@SpringBootApplication
public class TroubleshooterApplication {
    public static void main(String[] args) {
        SpringApplication.run(TroubleshooterApplication.class, args);
    }

    // CORS 설정
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        // JWT 로그인을 위해 클라이언트에서 X-AUTH-TOKEN에더에 접근 - 테스트를 위한 모든 접근 허용 작성
                        .exposedHeaders("*")
//                       클라이언트에서 쿠키를 받을수 있도록 허용
                        // 해당 코드 활성화시, 클라이언트쪽에서 응답 메세지가 정상 작동 안되는 현상 발생
                        // blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource.
//                        .allowCredentials(true)
//                       접근할수있도록 IP 주소 입력
                        .allowedOrigins("*");
            }
        };
    }
}