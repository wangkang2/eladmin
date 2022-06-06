package com.wk.service.system.impl;/**
 * @Author: WANGKANG
 * @Date: 2022/6/2 17:52
 * @Description: 
 */


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wk.config.security.dto.AuthorityDto;
import com.wk.entity.system.SysMenu;
import com.wk.entity.system.SysRole;
import com.wk.entity.system.dto.RoleDto;
import com.wk.entity.system.dto.UserDto;
import com.wk.mapper.system.SysMenuMapper;
import com.wk.mapper.system.SysRoleMapper;
import com.wk.service.system.SysRoleService;
import com.wk.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 
 * @author wangkang
 * @date 2022/06/02 17:52
 **/

@Service
@CacheConfig(cacheNames = "role")
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    @Cacheable(key = "'auth:' + #p0")
    public List<SysRole> findSysRoleByUserId(long userId) {
        QueryWrapper<SysRole> sysRoleQueryWrapper = new QueryWrapper<>();
        sysRoleQueryWrapper.eq("sys_users_roles.user_id",userId);
        sysRoleQueryWrapper.eq("sys_role.enabled","1");
        sysRoleQueryWrapper.apply("sys_users_roles.role_id = sys_role.role_id");
        List<SysRole> sysRoleList = sysRoleMapper.findSysRoleByUserId(sysRoleQueryWrapper);
        return sysRoleList;
    }

    @Override
    public List<RoleDto> findRoleDtoByUserId(long userId) {

        List<RoleDto> roleDtos = new ArrayList<>();
        List<SysRole> sysRoles = findSysRoleByUserId(userId);
        for(SysRole sysRole:sysRoles){
            RoleDto roleDto = new RoleDto();
            BeanUtils.copyProperties(sysRole,roleDto);

            QueryWrapper<SysMenu> sysMenuQueryWrapper = new QueryWrapper<>();
            sysMenuQueryWrapper.eq("sys_roles_menus.role_id",sysRole.getRoleId());
            sysMenuQueryWrapper.eq("sys_menu.enabled","1");
            sysMenuQueryWrapper.apply("sys_roles_menus.role_id = sys_menu.role_id");
            List<SysMenu> sysMenuList = sysMenuMapper.findSysMenuByRoleId(sysMenuQueryWrapper);
            roleDto.setSysMenus(sysMenuList);
            roleDtos.add(roleDto);
        }

        return roleDtos;
    }

    @Override
    public List<AuthorityDto> mapToGrantedAuthorities(UserDto userDto) {
        Set<String> permissions = new HashSet<>();
        // 如果是管理员直接返回
        if (userDto.getIsAdmin()) {
            permissions.add("admin");
            return permissions.stream().map(AuthorityDto::new)
                    .collect(Collectors.toList());
        }
        List<RoleDto> roles = findRoleDtoByUserId(userDto.getUserId());
        permissions = roles.stream().flatMap(role -> role.getSysMenus().stream())
                .map(SysMenu::getPermission)
                .filter(StringUtils::isNotBlank).collect(Collectors.toSet());
        return permissions.stream().map(AuthorityDto::new)
                .collect(Collectors.toList());
    }
}
