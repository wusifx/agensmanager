package cn.wusifx.agensmanager;

import cn.wusifx.agensmanager.filter.ApiValidateFilter;
import cn.wusifx.agensmanager.filter.SupermanValidateFilter;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public FilterRegistrationBean<ApiValidateFilter> apiValidateFilter() {
        FilterRegistrationBean<ApiValidateFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ApiValidateFilter());
        registrationBean.addUrlPatterns("/api/*");
        return registrationBean;
    }
    @Bean
    public FilterRegistrationBean<SupermanValidateFilter> supermanValidateFilter() {
        FilterRegistrationBean<SupermanValidateFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SupermanValidateFilter());
        registrationBean.addUrlPatterns("/superman/*");
        return registrationBean;
    }

    @Getter
    @Value("${error.redirect.url}")
    private String redirectUrl;
}
