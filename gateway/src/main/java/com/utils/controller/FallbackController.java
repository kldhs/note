package com.utils.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebExchangeDecorator;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: rds-cloud-framework</p>
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2020/1/6 16:33 </p>
 *
 * @author shichangcheng
 * @version V1.0
 */
@RestController
@RequestMapping("/fallback")
@Slf4j
public class FallbackController {

    @RequestMapping("")
    public Object fallback(ServerWebExchange exchange) {
        Map<String, Object> result = new HashMap<>();
        Exception exception = exchange.getAttribute(ServerWebExchangeUtils.HYSTRIX_EXECUTION_EXCEPTION_ATTR);
        ServerWebExchange delegate = ((ServerWebExchangeDecorator) exchange).getDelegate();
        log.error("接口调用失败，URL={}", delegate.getRequest().getURI(), exception);
        result.put("code", 500);
        result.put("msg", "系统异常，请稍后重试");
        return result;
    }

}
