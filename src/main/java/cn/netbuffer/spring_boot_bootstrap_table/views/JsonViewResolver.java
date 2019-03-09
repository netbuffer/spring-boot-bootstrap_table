package cn.netbuffer.spring_boot_bootstrap_table.views;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import java.util.Locale;

public class JsonViewResolver implements ViewResolver {

    @Override
    public View resolveViewName(String viewName, Locale locale)
            throws Exception {
        FastJsonJsonView jsonView=new FastJsonJsonView();
        return jsonView;
    }

}