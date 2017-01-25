package cn.com.ttblog.spring_boot_bootstrap_table.config;

import cn.com.ttblog.spring_boot_bootstrap_table.interceptor.SpringMvcInterceptor;
import cn.com.ttblog.spring_boot_bootstrap_table.views.JsonViewResolver;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.ArrayList;
import java.util.List;

/**
 *  spring boot默认的错误处理由org.springframework.boot.autoconfigure.web.BasicErrorController处理，
 */
@Configuration
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter{

    @Override
    public void configureContentNegotiation(
            ContentNegotiationConfigurer configurer) {
        configurer.ignoreAcceptHeader(false).defaultContentType(MediaType.TEXT_HTML);
    }

    // 在此---配置ContentNegotiatingViewResolver,通过此代理到不同的viewResolover
    @Bean
    public ViewResolver contentNegotiatingViewResolver(
            ContentNegotiationManager manager) {
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setContentNegotiationManager(manager);
        List<ViewResolver> resolvers = new ArrayList<ViewResolver>();
        // jsp view resolver
        resolvers.add(internalResourceViewResolver());
        JsonViewResolver jsonViewResolver=new JsonViewResolver();
        resolvers.add(jsonViewResolver);
        resolver.setViewResolvers(resolvers);
        return resolver;
    }

    @Bean
    public ViewResolver internalResourceViewResolver () {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setViewClass(JstlView.class);
        return viewResolver;
    }

    // 注册自定义拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SpringMvcInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/static")
                .excludePathPatterns("/css")
                .excludePathPatterns("/image")
                .excludePathPatterns("/js")
                .excludePathPatterns("/*.html")
                .excludePathPatterns("/favicon.ico");
    }

    // 文件上传设置
    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(1000000);
        return multipartResolver;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
