//package com.example.userservice.security;
//
//import com.example.userservice.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;
//import org.springframework.core.env.Environment;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.ObjectPostProcessor;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.util.matcher.IpAddressMatcher;
//import org.springframework.stereotype.Component;
//
//@Component
//@EnableWebSecurity(debug = true)
//@EnableMethodSecurity
////@RequiredArgsConstructor
//public class WebSecurity   {
//
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//    private ObjectPostProcessor<Object> objectPostProcessor;
//
//    private UserService userService;
//    private Environment env;
//    private static final String[] AUTH_WHITELIST = {
//           "/**", "/user/**", "/health_check, /login"
//    };
//    public WebSecurity(Environment env, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
//        this.env = env;
//        this.userService = userService ;
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//    }
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        //권한 작업
//        http.
//                csrf(CsrfConfigurer::disable)//csrf사용안함(사이트 위변조 요청 방지)근데이게 무슨 문법이냐 도대체
//                .authorizeHttpRequests(authorize ->
//                        authorize
//                                .requestMatchers("/**").permitAll()
//                                .anyRequest().authenticated());
////                                .requestMatchers(AUTH_WHITELIST)
////                                .permitAll()
////                                  .requestMatchers(new IpAddressMatcher("127.0.0.1"))
////                                .permitAll()
////                       );
// //               .addFilter(new AuthenticationFilter())
////                .formLogin(formLogin -> formLogin
////                        .loginPage("/login")
////                        .permitAll()
////                )
////                .rememberMe(Customizer.withDefaults());
//                //.rememberMe(Customizer.withDefaults());
//
//        //AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
//
//        return http.build();
//    }
//
//    private AuthenticationFilter getAutenticationFilter() throws Exception {
//        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
//        AuthenticationManagerBuilder builder = new AuthenticationManagerBuilder(objectPostProcessor);
//        authenticationFilter.setAuthenticationManager(authenticationManager(builder));
//
//        return authenticationFilter;
//    }
//
//    //select pwd from users where email =  ?
//    // dp_pwd(encrypted) == input_pwd(encryted)로 변경 필요
//
//    private AuthenticationManager authenticationManager(AuthenticationManagerBuilder auth) throws Exception {
//        //auth.userDetailService(UserService).passwordEncoder(bCrytPasswordEncoder);
//        //       auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
////        auth.inMemoryAuthentication()
////                .withUser(("user")).password(bCryptPasswordEncoder.encode("1234")).roles("USER")
////                        .and()
////                        .withUser("admin").password(bCryptPasswordEncoder.encode("1234")).roles("ADMIN");
//        return auth.build();
//    }
//}
////
////
