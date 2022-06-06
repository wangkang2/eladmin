package com.wk.entity.system.dto;

import com.wk.entity.system.SysMenu;
import com.wk.entity.system.SysRole;
import lombok.Data;

import java.util.List;

@Data
public class RoleDto extends SysRole {

    private List<SysMenu> sysMenus;

}
