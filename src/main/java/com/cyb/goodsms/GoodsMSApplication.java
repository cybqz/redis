package com.cyb.goodsms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author CYB
 */
@SpringBootApplication(scanBasePackages = {"com.cyb.*"})
@MapperScan({"com.cyb.goodsms.dao"})
public class GoodsMSApplication {

    public static void main(String[] args) {

        SpringApplication.run(GoodsMSApplication.class, args);
    }
}
