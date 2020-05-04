package com.consumer;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lzq
 * @date 2018/10/25
 */
@SpringBootApplication
@EnableDubbo(scanBasePackages = "com.consumer")
public class ConsumerApplication implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @Override
    public void run(String... strings) {
        logger.info("服务器已起动");
    }

}
