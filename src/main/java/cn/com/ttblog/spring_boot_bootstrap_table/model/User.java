package cn.com.ttblog.spring_boot_bootstrap_table.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Document(collection = "user")
@XmlRootElement
public class User implements Serializable {

    private static final long serialVersionUID = 4193866401992779318L;
    private String id;
    @Size(min = 1, max = 20, message = "{用户名长度必须在1到20个字符之间}")
    private String name;
    private String sex;
    @NotNull(message = "年龄不能为空并且在1-150之间")
    @Range(min = 1, max = 150)
    private Integer age;
    private String phone;
    private String deliveryaddress;
    private Integer adddate;
    private transient String comments;
    //1是admin 0不是admin
    private Integer isAdmin;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getDeliveryaddress() {
        return deliveryaddress;
    }

    public void setDeliveryaddress(String deliveryaddress) {
        this.deliveryaddress = deliveryaddress == null ? null : deliveryaddress.trim();
    }

    public Integer getAdddate() {
        return adddate;
    }

    public void setAdddate(Integer adddate) {
        this.adddate = adddate;
    }

    public User(String name, String sex, Integer age, String phone, String deliveryaddress, Integer adddate,
                String comments) {
        super();
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.phone = phone;
        this.deliveryaddress = deliveryaddress;
        this.adddate = adddate;
        this.comments = comments;
    }

    public User() {

    }

    public void init() {
        System.out.println("user init!");
    }

    public void syn() {
        synchronized (this) {
            System.out.println("syn!");
            try {
                TimeUnit.SECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void syn2() {
        System.out.println("syn2!");
        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        //允许打印transient成员
        return ToStringBuilder.reflectionToString(this, null, true);
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }
}