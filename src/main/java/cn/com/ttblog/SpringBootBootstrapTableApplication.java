package cn.com.ttblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;

/**
 * 禁用Thymeleaf的自动配置，使用自己的配置
 */
@SpringBootApplication(exclude = ThymeleafAutoConfiguration.class)
public class SpringBootBootstrapTableApplication{

    public static void main(String[] args) {
        SpringApplication.run(SpringBootBootstrapTableApplication.class, args);
    }

}
