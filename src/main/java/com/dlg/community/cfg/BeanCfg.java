package com.dlg.community.cfg;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;

// 相当于spring的application.xml的注解版
// @Configuration
public class BeanCfg {

    public ModelAndView getModelAndView(){
        ModelAndView mav = new ModelAndView();
        return mav;
    }

}
