package com.example.springboottest.bean;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.Date;


@Data
@MappedSuperclass
public class BasicEntity {
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name ="OBJ_ID",nullable=false,length = 36,columnDefinition="varchar")
    private String objId;
    @Column(name="create_date")
    private Date createDate;
    @Column(name="create_user")
    private String createUser;
    @Column(name="modify_user")
    private String modifyUser;
    @Column(name="modify_date")
    private Date modifyDate;
}
