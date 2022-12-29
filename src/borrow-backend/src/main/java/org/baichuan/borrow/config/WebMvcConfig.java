package org.baichuan.borrow.config;


import lombok.extern.slf4j.Slf4j;
import org.baichuan.borrow.util.CollectionIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * 设置静态资源映射
     * @param registry
     */

    @Autowired
    CollectionIdGenerator collectionIdGenerator;

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始进行静态资源映射...");
        collectionIdGenerator.initialize();
      //  registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

}
