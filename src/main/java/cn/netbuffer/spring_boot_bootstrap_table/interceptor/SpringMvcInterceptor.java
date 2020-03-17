package cn.netbuffer.spring_boot_bootstrap_table.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SpringMvcInterceptor extends HandlerInterceptorAdapter{

    private static final Logger LOG= LoggerFactory.getLogger(SpringMvcInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOG.debug("=============================================================prehandle interceptor");
        LOG.debug("request.getRequestURI():{},request.getRemoteAddr():{}",request.getRequestURI(),request.getRemoteAddr());
        LOG.debug("=============================================================prehandle interceptor");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LOG.debug("postHandle interceptor");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LOG.debug("afterCompletion interceptor");
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOG.debug("afterConcurrentHandlingStarted interceptor");
    }
}
