package com.glyxybxhtxt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.glyxybxhtxt.dao")
public class GlyxybxhtxtApplication {
    public static void main(String[] args) {
        SpringApplication.run(GlyxybxhtxtApplication.class, args);
    }
}
