package cn.netbuffer.spring_boot_bootstrap_table.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 使用lombok简化代码 https://projectlombok.org/features/index.html
 * @package cn.netbuffer.sssbootstrap_table.model
 * @author netbuffer
 */
@Entity
@Table(name = "card")
public class Card implements Serializable{
	private Long userId;
	private String cardNo;
    public Card(){

    }
    public Card(Long userId, String cardNo) {
        this.userId = userId;
        this.cardNo = cardNo;
    }

	@Id
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

	@Column(name = "card_no")
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}