package com.wk.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wk.entity.system.SysMenu;
import com.wk.entity.system.SysRole;
import com.wk.entity.system.dto.MenuDto;
import com.wk.mapper.system.SysMenuMapper;
import com.wk.mapper.system.SysRoleMapper;
import com.wk.service.system.SysMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<MenuDto> findByUserId(Long currentUserId) {

        QueryWrapper<SysRole> sysRoleQueryWrapper = new QueryWrapper<>();
        sysRoleQueryWrapper.eq("sys_users_roles.user_id",currentUserId);
        sysRoleQueryWrapper.eq("sys_role.enabled","1");
        sysRoleQueryWrapper.apply("sys_users_roles.role_id = sys_role.role_id");
        List<SysRole> sysRoleList = sysRoleMapper.findSysRoleByUserId(sysRoleQueryWrapper);


        List<Long> roleIds = sysRoleList.stream().map(SysRole::getRoleId).collect(Collectors.toList());

        QueryWrapper<SysMenu> sysMenuQueryWrapper = new QueryWrapper<>();
//        sysRoleQueryWrapper.in("sys_roles_menus.role_id",roleIds);
//        sysRoleQueryWrapper.eq("sys_menu.enabled","1");
//        sysMenuQueryWrapper.ne("sys_menu.type","2");
//        sysRoleQueryWrapper.apply("sys_roles_menus.menu_id = sys_menu.menu_id");
        sysMenuQueryWrapper.eq("menu_id",1);


        List<SysMenu> sysMenuList = sysMenuMapper.selectList(sysMenuQueryWrapper);

        List<MenuDto> menuDtos= new ArrayList<>();
        for(SysMenu sysMenu:sysMenuList){
            MenuDto menuDto = new MenuDto();
            BeanUtils.copyProperties(sysMenu,menuDto);
            menuDtos.add(menuDto);
        }

        return menuDtos;
    }

    @Override
    public List<MenuDto> buildTree(List<MenuDto> menuDtos) {
        List<MenuDto> trees = new ArrayList<>();
        Set<Long> ids = new HashSet<>();
        for (MenuDto menuDTO : menuDtos) {
            if (menuDTO.getPid() == null) {
                trees.add(menuDTO);
            }
            for (MenuDto it : menuDtos) {
                if (menuDTO.getMenuId().equals(it.getPid())) {
                    if (menuDTO.getChildren() == null) {
                        menuDTO.setChildren(new ArrayList<>());
                    }
                    menuDTO.getChildren().add(it);
                    ids.add(it.getMenuId());
                }
            }
        }
        if(trees.size() == 0){
            trees = menuDtos.stream().filter(s -> !ids.contains(s.getMenuId())).collect(Collectors.toList());
        }
        return trees;
    }
}
