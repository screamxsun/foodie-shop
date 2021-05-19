package com.imooc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;
import tk.mybatis.spring.annotation.MapperScan;

@Slf4j
@MapperScan(value = "com.imooc.mapper")
@ComponentScan(basePackages = {"com.imooc","org.n3r.idworker"})
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Application.class);
        ConfigurableEnvironment environment = springApplication.run(args).getEnvironment();
        // 1. 打印项目地址
        // 2. 打印doc地址
        log.info("swagger url => http://localhost:{}/doc.html",environment.getProperty("server.port"));
    }
}
