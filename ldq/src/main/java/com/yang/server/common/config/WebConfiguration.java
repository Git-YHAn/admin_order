package com.yang.server.common.config;

import com.yang.server.common.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        InterceptorRegistration ir = registry.addInterceptor(loginInterceptor);
        // 配置拦截的路径
        ir.addPathPatterns("/**");
        // 还可以在这里注册其它的拦截器
        // registry.addInterceptor(new OtherInterceptor()).addPathPatterns("/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").exposedHeaders(HttpHeaders.AUTHORIZATION)
                .allowedHeaders("X-Requested-With", HttpHeaders.ORIGIN,  HttpHeaders.CONTENT_TYPE, HttpHeaders.ACCEPT, HttpHeaders.AUTHORIZATION)
                .allowedMethods("GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH").allowedOrigins("*").maxAge(300);
        super.addCorsMappings(registry);
    }

//    @Bean
    public ReloadableResourceBundleMessageSource getMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:org/hibernate/validator/ValidationMessages", "classpath:validation");
        messageSource.setUseCodeAsDefaultMessage(false);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(60);
        return messageSource;
    }
}
