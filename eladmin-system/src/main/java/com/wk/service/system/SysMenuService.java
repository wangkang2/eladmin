package com.wk.service.system;

import com.wk.entity.system.dto.MenuDto;
import com.wk.entity.system.vo.MenuVo;

import java.util.List;

public interface SysMenuService {
    List<MenuDto> findByUserId(Long currentUserId);

    List<MenuDto> buildTree(List<MenuDto> menuDtos);

    List<MenuVo> buildMenus(List<MenuDto> menuDtos);
}
