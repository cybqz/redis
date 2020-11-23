package com.cyb.goodsms.dao;

import com.cyb.goodsms.domain.Parames;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ParamesMapper {

    int deleteByPrimaryKey(String id);

    int insert(Parames record);

    int insertSelective(Parames record);

    Parames selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Parames record);

    int updateByPrimaryKey(Parames record);

    Parames selectOneByName(String name);

    List<Parames> selectByParames(Parames record);
}