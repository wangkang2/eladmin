package com.wk.entity.system.dto;

import com.wk.entity.system.SysMenu;
import lombok.Data;

import java.util.List;

@Data
public class MenuDto extends SysMenu {

    private List<MenuDto> children;
}
