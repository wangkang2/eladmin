package com.wk.mapper.system;/**
 * @Author: WANGKANG
 * @Date: 2022/6/1 19:27
 * @Description: 
 */


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wk.entity.system.SysDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Set;


/**
 * dept mapper
 * @author wangkang
 * @date 2022/06/01 19:27
 **/


@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {

    @Select("SELECT sys_dept.*  FROM sys_dept, sys_roles_depts ${ew.customSqlSegment} ")
    Set<SysDept> findSysDeptByRoleId(@Param("ew") QueryWrapper<SysDept> sysDeptQueryWrapper);
}
