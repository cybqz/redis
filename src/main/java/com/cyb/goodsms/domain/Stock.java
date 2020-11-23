package com.cyb.goodsms.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Stock {
    private String id;

    private String supplierName;

    private String supplierPhone;

    private String goodsName;

    private String goodsCategory;

    private Integer stockInCount;

    private String carNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createDateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateDateTime;

    public Stock(String id, String supplierName, String supplierPhone, String goodsName, String goodsCategory, Integer stockInCount, String carNo, Date createDateTime, Date updateDateTime) {
        this.id = id;
        this.supplierName = supplierName;
        this.supplierPhone = supplierPhone;
        this.goodsName = goodsName;
        this.goodsCategory = goodsCategory;
        this.stockInCount = stockInCount;
        this.carNo = carNo;
        this.createDateTime = createDateTime;
        this.updateDateTime = updateDateTime;
    }
}