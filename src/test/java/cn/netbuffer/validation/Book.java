package cn.netbuffer.validation;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class Book {

    @NotNull(message = "书籍名称不能为空")
    private String name;
    private Date createTime;
    @Length(min = 4, max = 20, message = "author字符长度受限")
    private String author;

}
