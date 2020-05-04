package com.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.api.ProviderService;
/**
 *
 * @author lzq
 * @date 2018/10/25
 */
@Service(version="1.0.0")
public class ProviderServiceImpl implements ProviderService {

    @Override
    public String sayHello() {
        return "你好 dubbo";
    }
}
