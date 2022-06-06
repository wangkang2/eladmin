package com.wk.mapper.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wk.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: WANGKANG
 * @Date: 2022/6/2 17:01
 * @Description:
 */

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    @Select("SELECT sys_role.*  FROM sys_role, sys_users_roles ${ew.customSqlSegment} ")
    List<SysRole> findSysRoleByUserId(@Param("ew") QueryWrapper<SysRole> sysRoleQueryWrapper);
}
