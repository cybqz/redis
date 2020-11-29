package com.cyb.redisclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author CYB
 */

@SpringBootApplication(
        scanBasePackages = {"com.cyb.redisclient.*"}
)
public class RedisClientApplication {

    public static void main(String[] args) {

        SpringApplication.run(RedisClientApplication.class, args);
    }
}
