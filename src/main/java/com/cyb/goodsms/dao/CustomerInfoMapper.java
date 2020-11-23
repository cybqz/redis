package com.cyb.goodsms.dao;

import com.cyb.common.pagination.Pagination;
import com.cyb.goodsms.domain.CustomerInfo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CustomerInfoMapper {
    int countByExample(CustomerInfo record);

    int deleteById(String id);

    int insert(CustomerInfo record);

    CustomerInfo selectById(String id);

    List<CustomerInfo> selectByExample(CustomerInfo record, Pagination pagination);

    int updateById(@Param("record") CustomerInfo record);
}