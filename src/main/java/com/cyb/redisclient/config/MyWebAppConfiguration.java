package com.cyb.redisclient.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class MyWebAppConfiguration extends WebMvcConfigurationSupport {


    public static String V_PATH = "/path/";

    /**
     * @Description:
     * 对文件的路径进行配置, 创建一个虚拟路径/Path/**
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //默认访问static文件
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }

}
