package cn.com.ttblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;

/**
 * 使用@ServletComponentScan注解开启servlet、filter、listener的扫描支持，也可以通过代码来注册servlet等
 * http://blog.csdn.net/catoop/article/details/50501686
 */
@ServletComponentScan
@SpringBootApplication
@ImportResource(value = {"classpath:interceptor.xml"})
public class SpringBootBootstrapTableApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootBootstrapTableApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootBootstrapTableApplication.class, args);
    }

}
