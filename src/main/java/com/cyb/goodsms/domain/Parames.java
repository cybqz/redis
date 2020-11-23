package com.cyb.goodsms.domain;

import lombok.Data;

@Data
public class Parames {

    private String id;

    private String name;

    private String value;

    private String group;

    private int order;

    private String remark;

    public Parames(String id, String name, String value, String group) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.group = group;
    }

}