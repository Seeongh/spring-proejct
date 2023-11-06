package com.example.api.gateway.service.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration //이 정보를 등록 , 이 파일은 yml파일 설정과 같은내용임
public class FilterConfig {
 //   @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/first-service/**")
                        .filters(f-> f.addRequestHeader("first-request","first-request-header")
                                        .addResponseHeader("first-response","first-response-header"))
                        .uri("http://localhost:"))
                .build();
    }
}
