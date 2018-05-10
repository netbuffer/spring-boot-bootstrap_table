package cn.com.ttblog.spring_boot_bootstrap_table.controller;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logback")
public class LogbackController {

    static final LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();

    @RequestMapping("/status/print")
    public String printStatus() {
        StatusPrinter.print(lc);
        return "success";
    }
}
