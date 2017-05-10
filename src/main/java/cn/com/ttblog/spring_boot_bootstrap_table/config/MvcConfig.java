package cn.com.ttblog.spring_boot_bootstrap_table.config;

import cn.com.ttblog.spring_boot_bootstrap_table.interceptor.SpringMvcInterceptor;
import cn.com.ttblog.spring_boot_bootstrap_table.views.JsonViewResolver;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import java.util.ArrayList;
import java.util.List;

/**
 * spring boot默认的错误处理由org.springframework.boot.autoconfigure.web.BasicErrorController处理，
 */
@Configuration
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    /**
     * 映射所有请求到templates目录下
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/templates/");
    }

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
        resolvers.add(jsonViewResolver());
        resolvers.add(thymeleafViewResolver());
        resolver.setViewResolvers(resolvers);
        return resolver;
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

    @Bean
    public ViewResolver jsonViewResolver() {
        JsonViewResolver jsonViewResolver = new JsonViewResolver();
        return jsonViewResolver;
    }

    @Bean
    public ViewResolver thymeleafViewResolver() {
        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.setTemplateResolver(springTemplateResolver());
        thymeleafViewResolver.setTemplateEngine(springTemplateEngine);
        thymeleafViewResolver.setCharacterEncoding("utf-8");
        return thymeleafViewResolver;
    }

    @Bean
    public ITemplateResolver springTemplateResolver() {
        SpringResourceTemplateResolver springResourceTemplateResolver = new SpringResourceTemplateResolver();
        springResourceTemplateResolver.setCharacterEncoding("utf-8");
        springResourceTemplateResolver.setCacheable(false);
        springResourceTemplateResolver.setPrefix("classpath:/templates/");
        springResourceTemplateResolver.setSuffix(".html");
        springResourceTemplateResolver.setTemplateMode("HTML5");
        springResourceTemplateResolver.setApplicationContext(applicationContext);
        return springResourceTemplateResolver;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
