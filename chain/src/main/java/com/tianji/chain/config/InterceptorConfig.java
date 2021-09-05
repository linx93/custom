package com.tianji.chain.config;

import com.tianji.chain.interceptor.CheckReq;
import com.tianji.chain.service.CheckReqService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 自定义拦截器配置
 * @author linx
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final CheckReqService checkReqService;

    public InterceptorConfig(CheckReqService checkReqService) {
        this.checkReqService = checkReqService;
    }

    /**
     * 注册自定义拦截器
     */
   /* @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CheckReq(checkReqService))
                .addPathPatterns("/**").excludePathPatterns("/doc.html", "/webjars/**", "/swagger-resources/**");
    }*/
}

