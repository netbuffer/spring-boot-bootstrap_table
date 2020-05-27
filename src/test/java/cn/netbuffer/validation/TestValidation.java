package cn.netbuffer.validation;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

@Slf4j
public class TestValidation {

    private Validator validator;
    private ExecutableValidator executableValidator;

    @Before
    public void before() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        executableValidator = validator.forExecutables();
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

    @Test
    public void testValidateParameters() throws NoSuchMethodException {
        Book book = new Book();
        Set<ConstraintViolation<Book>> constraintViolations = executableValidator
                .validateParameters(book, book.getClass().getMethod("print", Book.class), new Object[]{book}, Book.Group.class);
        log.info("invoke print method validate:{}", constraintViolations);
    }

    @Test
    public void testValidateReturnValue() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Book book = new Book();
        Method method = book.getClass().getMethod("build");
        Object returnValue = method.invoke(book);
        Set<ConstraintViolation<Book>> constraintViolations = executableValidator
                .validateReturnValue(book, method, returnValue, Book.Group.class);
        log.info("invoke build method validate:{}", constraintViolations);
    }

    @Test
    public void testValidateConstructorParameters() throws NoSuchMethodException {
//        Set<ConstraintViolation<Book>> constraintViolations = executableValidator.validateConstructorParameters();
    }

}
