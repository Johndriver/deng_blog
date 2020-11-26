package com.just;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan(basePackages = {"com.just.mapper"})
@SpringBootApplication
public class DengBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(DengBlogApplication.class, args);
    }
}
