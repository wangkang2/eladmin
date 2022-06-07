package com.wk.mapper.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wk.entity.system.SysMenu;
import com.wk.entity.system.dto.MenuDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    @Select("SELECT sys_menu.menu_id as id,sys_menu.*  FROM sys_menu, sys_roles_menus ${ew.customSqlSegment} ")
    List<SysMenu> findSysMenuByRoleId(@Param("ew") QueryWrapper<SysMenu> sysMenuQueryWrapper);

    @SelectProvider(type = SysMenuProvider.class,method = "findSysMenu")
    List<MenuDto> findSysMenu(List<Long> roleIds);

    class SysMenuProvider{
        public String findSysMenu(List<Long> roleIds){
            return new SQL(){{
                SELECT("sys_menu.menu_id as id,sys_menu.*");
                FROM("sys_menu, sys_roles_menus");
                WHERE("sys_menu.menu_id = sys_roles_menus.menu_id");
                WHERE("sys_menu.enabled = 1");
                WHERE("sys_menu.del_flag = 1");
                WHERE("sys_menu.type != 2");

                if(roleIds!=null && roleIds.size()>0){
                    StringBuffer str = new StringBuffer();
                    for(Long roleId:roleIds) {
                        str.append(roleId);
                        str.append(",");
                    }
                    WHERE("sys_roles_menus.role_id in ("+str.substring(0,str.length()-1) + ")");
                }

            }}.toString();
        }
    }
}
