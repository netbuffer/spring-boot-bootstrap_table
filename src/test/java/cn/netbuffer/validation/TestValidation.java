package cn.netbuffer.validation;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
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
        book.setAuthorAge(180);
        book.setCreateTime(DateTime.now().plusDays(-1).toDate());
//        book.setPages(null);
//        book.setPages(Lists.newArrayList());
//        book.setPages(Lists.newArrayList(1,2,3));
        Book s1 = new Book();
        book.setBooks(Lists.newArrayList(s1));
        Set<ConstraintViolation<Book>> constraintViolations = validator.validate(book);
        log.info("constraintViolations size:{} detail:{}", constraintViolations.size(), constraintViolations);
        constraintViolations.forEach(c -> {
            log.info("校验错误信息:{}", c.getMessage());
        });
    }

    @Test
    public void testValidateGroup() {
        Book book = new Book();
        Set<ConstraintViolation<Book>> constraintViolations = validator.validate(book, Book.AddBookGroup.class);
        log.info("AddBookGroup validate size:{} detail:{}", constraintViolations.size(), constraintViolations);
        constraintViolations.forEach(c -> {
            log.info("校验错误信息:{}", c.getMessage());
        });
    }

    @Test
    public void testSequenceGroup() {
        Book book = new Book();
        Set<ConstraintViolation<Book>> constraintViolations = validator.validate(book, Book.Group.class);
        log.info("SequenceGroup validate size:{} detail:{}", constraintViolations.size(), constraintViolations);
        constraintViolations.forEach(c -> {
            log.info("校验错误信息:{}", c.getMessage());
        });
    }

}
