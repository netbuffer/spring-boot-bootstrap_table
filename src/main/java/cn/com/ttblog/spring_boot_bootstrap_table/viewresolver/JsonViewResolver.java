package cn.com.ttblog.spring_boot_bootstrap_table.viewresolver;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

public class JsonViewResolver implements ViewResolver {

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        FastJsonJsonView jsonJsonView=new FastJsonJsonView();
		return jsonJsonView;
    }
}