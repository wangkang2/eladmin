package com.wk.service.system;

import com.wk.entity.system.dto.UserDto;

/**
 * @Author: WANGKANG
 * @Date: 2022/6/2 16:20
 * @Description:
 */
public interface SysUserService {

    UserDto getLoginData(String username);
}
