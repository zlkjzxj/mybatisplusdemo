package com.sunny.mybatisplusdemo.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunny.mybatisplusdemo.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description userMapper
 * @Author sunny
 * @Date 2019-07-16 11:24
 */
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user ${ew.customSqlSegment}")
    List<User> getAll(@Param(Constants.WRAPPER) Wrapper wrapper);

    @Select("select * from user ${ew.customSqlSegment}")
    IPage<User> selectUserPage(Page<User> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
