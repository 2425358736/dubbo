package com.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.api.ProviderService;

/**
 * @author lzq
 * @date 2018/10/25
 */
@RestController
public class ConsumerController {
    /**
     * 订阅消费的接口
     */
    @Reference(version = "1.0.0")
    private ProviderService providerService;

    @RequestMapping("/sayHello")
    String sayHello() {
        return providerService.sayHello();
    }
}
