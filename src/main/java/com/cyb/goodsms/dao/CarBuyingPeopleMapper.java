package com.cyb.goodsms.dao;

import com.cyb.common.pagination.Pagination;
import com.cyb.goodsms.domain.CarBuyingPeople;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CarBuyingPeopleMapper {
    int countByExample(CarBuyingPeople record);

    int deleteById(String id);

    int insert(CarBuyingPeople record);

    CarBuyingPeople selectById(String id);

    List<CarBuyingPeople> selectByExample(CarBuyingPeople record, Pagination pagination);

    int updateById(@Param("record") CarBuyingPeople record);
}