package com.utils.filter;

import feign.Request;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import java.util.List;
import java.util.function.Consumer;


/**
 */
@Slf4j
@Component
public class SessionAccessGatewayFilterFactory extends AbstractGatewayFilterFactory<SessionAccessGatewayFilterFactory.Config> {

    public static String AUTHORIZATION = "Authorization";
    public static String GET = "get";
    public static String POST = "post";
    public static String WEB = "application/x- www-form-urlencoded";

    public SessionAccessGatewayFilterFactory() {
        super(Config.class);
    }


    @Override
    public GatewayFilter apply(String routeId, Consumer<Config> consumer) {
        return null;
    }

    @Override
    public GatewayFilter apply(Consumer<Config> consumer) {
        return null;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (ServerWebExchange exchange, GatewayFilterChain chain) -> {
            log.info("GatewayFilter apply");

            ServerHttpRequest httpRequest = exchange.getRequest();
            String requestUri = httpRequest.getURI().getPath();
            final String method = httpRequest.getMethod().name();

            if (method.equals(Request.HttpMethod.OPTIONS)) {
                return chain.filter(exchange);
            }

            String token = getToken(httpRequest);

            boolean needValidToken = needValidToken(requestUri, config);
            if (!needValidToken) {
                return chain.filter(exchange);
            }

            ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
            if(null == token) {
                log.error("解析Token失败");
                String responseBody = "解析Token失败";
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.OK);
                byte[] bits = responseBody.getBytes(StandardCharsets.UTF_8);
                DataBuffer buffer = response.bufferFactory().wrap(bits);

                return response.writeWith(Mono.just(buffer));
            }

            return chain.filter(exchange.mutate().request(builder.build()).build());
        };
    }

    /**
     * 从Url参数、Http Header、Form 表单中获取Token
     *
     * @param request
     * @return
     */
    private String getToken(ServerHttpRequest request) {
        String token = null;

        //Http Header 中包含Token信息
        if (request.getHeaders().containsKey(AUTHORIZATION)) {
            token = request.getHeaders().get(AUTHORIZATION).get(0);
            return token;
        }

        // Get 请求参数中包含token
        if (GET.equalsIgnoreCase(request.getMethod().name())) {
            token = request.getQueryParams().getFirst("token");
            if (StringUtils.isNotBlank(token)) {
                return token;
            }

            token = request.getQueryParams().getFirst(AUTHORIZATION);

            if (StringUtils.isNotBlank(token)) {
                return token;
            }

            log.warn("method:get url:{} query token is null", request.getURI().getPath());
            return token;
        }

        // Post 请求参数中包含token
        if (POST.equalsIgnoreCase(request.getMethod().name())) {

            String contentType = request.getHeaders().getContentType().getType();

            // form 表单提交
            if (WEB.equals(contentType)) {
                token = request.getQueryParams().getFirst("token");

                if (StringUtils.isNotBlank(token)) {
                    return token;
                }

                log.warn("method:post url:{} query token is null", request.getURI().getPath());
                return token;
            }

            return token;
        }

        return token;
    }

    @Override
    public GatewayFilter apply(String routeId, Config config) {
        return null;
    }

    /**
     * 是否需要校验Token
     *
     * @param requestUri
     * @param config
     * @return true需要校验，false不需要校验
     */
    private boolean needValidToken(String requestUri, Config config) {
        boolean flag = true;
        if (null == config.getIgnorePath() || config.getIgnorePath().isEmpty()) {
            flag = false;
        } else {
            for (String s : config.getIgnorePath()) {
                if (requestUri.startsWith(s) || requestUri.contains(s)) {
                    flag = false;
                    break;
                }
            }
        }
        log.info("url {} need valid token,{}", requestUri, flag);
        return flag;
    }

    public static class Config {

        private List<String> ignorePath;

        public Config() {
            ignorePath = new ArrayList<>();
        }

        public List<String> getIgnorePath() {
            return ignorePath;
        }

        public void setIgnorePath(List<String> ignorePath) {
            this.ignorePath = ignorePath;
        }
    }

}
