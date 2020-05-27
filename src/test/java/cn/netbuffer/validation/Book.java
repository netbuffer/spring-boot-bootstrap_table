package cn.netbuffer.validation;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.*;
import javax.validation.groups.Default;
import java.util.Date;
import java.util.List;

@Data
public class Book {

    /**
     * 定义验证组
     */
    public interface AddBookGroup {
    }

    public interface UpdateBookGroup {
    }

    /**
     * 组序列
     * 按照定义的验证顺序来依次验证,当前面的验证规则通过才会执行后续验证
     * 规则注解中没有标记组的，从属于Default组
     */
    @GroupSequence(value = {
            AddBookGroup.class,
            UpdateBookGroup.class,
            Default.class
    })
    public interface Group {
    }

    @NotNull(message = "书籍名称不能为空", groups = {AddBookGroup.class})
    private String name;
    @FutureOrPresent
    private Date createTime;
    @Length(min = 4, max = 20, message = "author字符长度受限")
    private String author;
    @Min(value = 10)
    @Max(value = 150)
    private Integer authorAge;
    @NotNull(message = "页码不能为空", groups = {AddBookGroup.class, UpdateBookGroup.class})
    @Size(min = 1, max = Integer.MAX_VALUE, message = "页数非法")
    private List<Integer> pages;
    //级联验证
    private List<@Valid Book> books;

    public void print(@Valid Book book) {
        System.out.println("打印book信息:" + book.getName());
    }

    public @Valid Book build() {
        Book book = new Book();
        System.out.println("构造book信息:" + book);
        return book;
    }
}
