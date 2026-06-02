package com.qqq.ai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.qqq.ai.mapper")
@SpringBootApplication
public class AiDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiDemoApplication.class, args);
    }

}
