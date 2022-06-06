package com.wk.service.system;

import com.wk.config.security.dto.AuthorityDto;
import com.wk.entity.system.SysRole;
import com.wk.entity.system.dto.RoleDto;
import com.wk.entity.system.dto.UserDto;

import java.util.List;

/**
 * @Author: WANGKANG
 * @Date: 2022/6/2 17:52
 * @Description:
 */
public interface SysRoleService {

    List<SysRole> findSysRoleByUserId(long userId);

    List<RoleDto> findRoleDtoByUserId(long userId);

    List<AuthorityDto> mapToGrantedAuthorities(UserDto userDto);
}
