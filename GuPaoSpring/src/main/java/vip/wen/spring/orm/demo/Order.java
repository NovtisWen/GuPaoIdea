package vip.wen.spring.orm.demo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@Table(name = "t_order")
public class Order {

    @Column(name = "id")
    private Long id;
    @Column(name = "memeberId")
    private Long memeberId;
    @Column(name = "detail")
    private String detail;
    @Column(name = "createTime")
    private Long createTime;
    @Column(name = "createTimeFmt")
    private String createTimeFmt;

}
