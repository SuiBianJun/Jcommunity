package com.dlg.community.cfg;

import com.dlg.community.interceptor.UserLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private UserLoginInterceptor userLoginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(userLoginInterceptor).addPathPatterns("/**").excludePathPatterns("/", "/index", "/callback", "/question/**",
                "/reply/**");

        super.addInterceptors(registry);

    }
}
