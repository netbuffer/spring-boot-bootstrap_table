package cn.netbuffer.validation;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Slf4j
public class TestValidation {

    private Validator validator;

    @Before
    public void before() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void testSimpleRule() {
        Book book = new Book();
        book.setAuthor("a");
        Set<ConstraintViolation<Book>> constraintViolations = validator.validate(book);
        log.info("constraintViolations size:{} detail:{}", constraintViolations.size(), constraintViolations);
        constraintViolations.forEach(c -> {
            log.info("校验错误信息:{}", c.getMessage());
        });
    }

}
