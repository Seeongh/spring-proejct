package com.example.api.gateway.service.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {

    public GlobalFilter() {
        super(Config.class);
    }

    public GatewayFilter apply(Config config) {
        //Custeom pre filter
        return (exchange, chain) -> {
            ServerHttpRequest request =  exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();


            log.info("Global filter baseMessage: {}", config.getBaseMessage());

            if (config.isPreLogger()){
                log.info("Global filter Start: request id -> {}", request.getId());
            }

            //Custom Post Filter
            return chain.filter(exchange).then(Mono.fromRunnable(()-> {
                if(config.isPostLogger()) {
                    log.info("Global Filter End:response code -> {}", response.getStatusCode());
                }
            }));
        };
    }


    @Data //lombok으로 게터 세터
    public static class Config {
        //Put the configuration properties
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }

}
