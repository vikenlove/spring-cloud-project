package com.emerging.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
* @author:Faye.Wang
* @version 创建时间：2018年12月7日 上午9:51:46
* *处理swaggerUI访问
*/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer  {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		 registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/"); 
		 registry.addResourceHandler("swagger-ui.html") .addResourceLocations("classpath:/META-INF/resources/"); 
		 registry.addResourceHandler("/webjars/**") .addResourceLocations("classpath:/META-INF/resources/webjars/");
		WebMvcConfigurer.super.addResourceHandlers(registry);
	}
}
