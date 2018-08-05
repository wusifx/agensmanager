package cn.wusifx.agensmanager.filter;

import cn.wusifx.agensmanager.AppConfig;
import cn.wusifx.agensmanager.service.DeveloperService;
import liquibase.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SupermanValidateFilter implements Filter {
    @Autowired
    DeveloperService developerService;
    @Autowired
    AppConfig appConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String developerId = httpServletRequest.getParameter("developerId");
        String securityCode = httpServletRequest.getParameter("securityCode");
        String accessToken = httpServletRequest.getParameter("accessToken");
        HttpStatus status = HttpStatus.OK;
        if(!StringUtils.isEmpty(accessToken)){
            status = developerService.auth(accessToken);
        }else {
            status = developerService.auth(developerId,securityCode);
        }
        if(status== HttpStatus.OK) {
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            httpServletResponse.sendRedirect(String.format("/%s?status=%d",appConfig.getRedirectUrl(),status.value()));
        }
    }

    @Override
    public void destroy() {

    }
}
