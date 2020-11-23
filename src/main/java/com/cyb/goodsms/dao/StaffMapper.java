package com.cyb.goodsms.dao;

import com.cyb.common.pagination.Pagination;
import com.cyb.goodsms.domain.Staff;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StaffMapper {
    int countByExample(Staff record);

    int deleteById(String id);

    int insert(Staff record);

    Staff selectById(String id);

    List<Staff> selectByExample(Staff record, Pagination pagination);

    int updateById(@Param("record") Staff record);
}