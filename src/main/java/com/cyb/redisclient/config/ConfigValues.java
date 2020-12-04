package com.cyb.redisclient.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigValues {

    @Value("${spring.redis.host}")
    public String host;

    @Value("${spring.redis.port}")
    public String port;

    @Value("${spring.redis.password}")
    public String password;

    @Value("${server.servlet.context-path}")
    public String contextPath;


}
