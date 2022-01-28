package com.utils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {


    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    /**
     * 处理跨域请求
     * @return
     */
    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // 允许的请求头
        config.addAllowedHeader("*");
        // 允许的请求源 （如：http://localhost:8080）
        config.addAllowedOrigin("*");
        // 允许的请求方法 ==> GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE
        config.addAllowedMethod("*");
        // URL 映射 （如： /admin/**）
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", config);
        CorsWebFilter corsWebFilter = new CorsWebFilter(urlBasedCorsConfigurationSource);
        return corsWebFilter;
    }
}

