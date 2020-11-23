package com.cyb.goodsms.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CarBuyingPeople {
    private String id;

    private String buyingPeopleName;

    private String phone;

    private String description;

    private String buyName;

    private BigDecimal price;

    private String category;

    private BigDecimal profit;

    private String staffNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createDateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateDateTime;

    public CarBuyingPeople(String id, String buyingPeopleName, String phone, String description, String buyName, BigDecimal price,
                           String category, BigDecimal profit, String staffNo, Date createDateTime, Date updateDateTime) {
        this.id = id;
        this.buyingPeopleName = buyingPeopleName;
        this.phone = phone;
        this.description = description;
        this.buyName = buyName;
        this.price = price;
        this.category = category;
        this.profit = profit;
        this.staffNo = staffNo;
        this.createDateTime = createDateTime;
        this.updateDateTime = updateDateTime;
    }
}