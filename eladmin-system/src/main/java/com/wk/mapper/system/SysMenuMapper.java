package com.wk.mapper.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wk.entity.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    @Select("SELECT sys_menu.*  FROM sys_menu, sys_roles_menus ${ew.customSqlSegment} ")
    List<SysMenu> findSysMenuByRoleId(@Param("ew") QueryWrapper<SysMenu> sysMenuQueryWrapper);
}
