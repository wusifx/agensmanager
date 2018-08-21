package cn.wusifx.agensmanager;

import cn.wusifx.agensmanager.filter.ApiValidateFilter;
import cn.wusifx.agensmanager.filter.SupermanValidateFilter;
import cn.wusifx.agensmanager.service.DeveloperService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Configuration
public class AppConfig {
    @Autowired
    DeveloperService developerService;

    @Bean
    public FilterRegistrationBean<ApiValidateFilter> apiValidateFilter() {
        FilterRegistrationBean<ApiValidateFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ApiValidateFilter(developerService, this));
        registrationBean.addUrlPatterns("/api/*");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<SupermanValidateFilter> supermanValidateFilter() {
        FilterRegistrationBean<SupermanValidateFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SupermanValidateFilter(developerService, this));
        registrationBean.addUrlPatterns("/superman/*");
        return registrationBean;
    }
    @Bean
    @ConfigurationProperties(prefix = "webhook.rest.connection")
    public HttpComponentsClientHttpRequestFactory customHttpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory();
    }
    @Bean
    public RestTemplate webhookRestTemplate(){
        return new RestTemplate(customHttpRequestFactory());
    }
    @Getter
    @Value("${error.redirect.url}")
    private String redirectUrl;
}
