package com.cyb.goodsms.vo;

import lombok.Data;

import java.util.List;

@Data
public class UserCreateVO {


    private String userName;

    private String password;

    private int sex;

    private List<String> interestList;
}