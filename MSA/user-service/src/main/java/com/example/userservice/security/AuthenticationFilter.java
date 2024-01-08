//package com.example.userservice.security;
//
//import com.example.userservice.vo.RequestLogin;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//    public AuthenticationFilter() {
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//
//        try {
//            RequestLogin creds = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);
//            return getAuthenticationManager().authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            creds.getEmail(),
//                            creds.getPassword(),
//                            new ArrayList<>()
//                    )
//            );
//            //값을 토큰에 넣어서 변경해주는 매소드를 매니저에 넣으면 id,pw비교
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//
//    public void successfulAuthentication(HttpServletRequest request,
//                                             HttpServletResponse response,
//                                             FilterChain chain,
//                                             Authentication authResult) throws IOException {
//
//    }
//}