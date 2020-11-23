package com.cyb.goodsms.dao;

import com.cyb.common.pagination.Pagination;
import com.cyb.goodsms.domain.Stock;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StockMapper {
    int countByExample(Stock record);

    int deleteById(String id);

    int insert(Stock record);

    Stock selectById(String id);

    List<Stock> selectByExample(Stock record, Pagination pagination);

    int updateById(@Param("record") Stock record);
}