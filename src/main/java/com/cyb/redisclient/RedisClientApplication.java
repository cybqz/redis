package com.cyb.redisclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author CYB
 */
@SpringBootApplication(scanBasePackages = {"com.cyb.*"})
public class RedisClientApplication {

    public static void main(String[] args) {

        SpringApplication.run(RedisClientApplication.class, args);
    }
}
