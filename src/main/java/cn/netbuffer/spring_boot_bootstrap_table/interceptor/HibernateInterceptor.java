package cn.netbuffer.spring_boot_bootstrap_table.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.EmptyInterceptor;

@Slf4j
public class HibernateInterceptor extends EmptyInterceptor {

    public HibernateInterceptor() {
        log.info("register HibernateInterceptor");
    }

    @Override
    public String onPrepareStatement(String sql) {
        log.debug("execute sql:{},stack:{}", sql,Thread.currentThread().getStackTrace()[0]);
        return sql;
    }
}
