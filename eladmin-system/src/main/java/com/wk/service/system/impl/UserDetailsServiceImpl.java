package com.wk.service.system.impl;/**
 * @Author: WANGKANG
 * @Date: 2022/6/2 15:40
 * @Description: 
 */


import com.wk.config.security.dto.JwtUserDto;
import com.wk.config.security.service.UserCacheManager;
import com.wk.entity.system.dto.UserDto;
import com.wk.exception.BadRequestException;
import com.wk.exception.EntityNotFoundException;
import com.wk.service.system.DataService;
import com.wk.service.system.SysRoleService;
import com.wk.service.system.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 
 * @author wangkang
 * @date 2022/06/02 15:40
 **/

@Slf4j
@RequiredArgsConstructor
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SysUserService sysUserService;
    private final SysRoleService sysRoleService;
    private final DataService dataService;
    private final UserCacheManager userCacheManager;

    @Override
    public UserDetails loadUserByUsername(String username) {
        JwtUserDto jwtUserDto = userCacheManager.getUserCache(username);
        if(jwtUserDto == null){
            UserDto userDto;
            try {
                userDto = sysUserService.getLoginData(username);
            } catch (EntityNotFoundException e) {
                // SpringSecurity会自动转换UsernameNotFoundException为BadCredentialsException
                throw new UsernameNotFoundException(username, e);
            }
            if (userDto == null) {
                throw new UsernameNotFoundException("");
            } else {
                if (!userDto.getEnabled()) {
                    throw new BadRequestException("账号未激活！");
                }
                jwtUserDto = new JwtUserDto(
                        userDto,
                        dataService.getDeptIds(userDto),
                        sysRoleService.mapToGrantedAuthorities(userDto)
                );
                // 添加缓存数据
                userCacheManager.addUserCache(username, jwtUserDto);
            }
        }
        return jwtUserDto;
    }
}
