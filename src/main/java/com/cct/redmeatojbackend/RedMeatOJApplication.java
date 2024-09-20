package com.cct.redmeatojbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author cct
 */
@SpringBootApplication
@MapperScan("com.cct.redmeatojbackend.**.mapper")
public class RedMeatOJApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedMeatOJApplication.class, args);
    }
}
