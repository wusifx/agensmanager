package cn.wusifx.agensmanager.filter;

import cn.wusifx.agensmanager.AppConfig;
import cn.wusifx.agensmanager.service.DeveloperService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class ApiValidateFilter implements Filter {
    private DeveloperService developerService;
    private AppConfig appConfig;

    public ApiValidateFilter(DeveloperService developerService, AppConfig appConfig) {
        this.developerService = developerService;
        this.appConfig = appConfig;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        log.info("Validation Request  {} : {}", req.getMethod(), req.getRequestURI());
        filterChain.doFilter(servletRequest, servletResponse);
        log.info("Validation Response :{}", res.getContentType());
    }

    @Override
    public void destroy() {

    }
}
