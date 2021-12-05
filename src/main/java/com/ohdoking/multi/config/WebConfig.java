package com.ohdoking.multi.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.servlet.Filter;

@Configuration
public class WebConfig {

    @Bean
    public Filter filter(){
        ShallowEtagHeaderFilter filter=new ShallowEtagHeaderFilter();
        return filter;
    }

    @Bean
    public FilterRegistrationBean<ShallowEtagHeaderFilter> shallowEtagHeaderFilter() {
        FilterRegistrationBean<ShallowEtagHeaderFilter> filterRegistrationBean
                = new FilterRegistrationBean<>( new ShallowEtagHeaderFilter());
        filterRegistrationBean.addUrlPatterns("/test/*");
        filterRegistrationBean.setName("etagFilter");
        return filterRegistrationBean;
    }
}
