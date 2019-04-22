package vip.wen.spring.orm.demo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;


@Data
@Table(name = "t_member")
public class Member {

    @Column(name = "id")
    private Long id;
    @Column(name = "addr")
    private String addr;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private Integer age;

}
