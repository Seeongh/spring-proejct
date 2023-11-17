package com.example.userservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
@Configuration
@EnableWebSecurity //security 활성화 -> security filter가 spring filterchain에 등록됨
public class SecurityConfig{
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //권한 작업
       http.
               csrf(CsrfConfigurer::disable);//csrf사용안함(사이트 위변조 요청 방지)근데이게 무슨 문법이냐 도대체
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/**").permitAll()
               //                 .anyRequest().authenticated());
//                                .requestMatchers(AUTH_WHITELIST)
//                                .permitAll()
//                                  .requestMatchers(new IpAddressMatcher("127.0.0.1"))
//                                .permitAll()
//                       );
 //               .addFilter(new AuthenticationFilter())
//                .formLogin(formLogin -> formLogin
//                        .loginPage("/login")
//                        .permitAll()
//                )
//                .rememberMe(Customizer.withDefaults());
                //.rememberMe(Customizer.withDefaults());

        //AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

        return http.build();
    }


}
