package com.cyb.goodsms.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Staff {
    private String id;

    private String name;

    private String phone;

    private String email;

    private Integer sex;

    private String department;

    private String no;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createDateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateDateTime;

    public Staff(String id, String name, String phone, String email, Integer sex, String department, String no, Date createDateTime, Date updateDateTime) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.sex = sex;
        this.department = department;
        this.no = no;
        this.createDateTime = createDateTime;
        this.updateDateTime = updateDateTime;
    }
}