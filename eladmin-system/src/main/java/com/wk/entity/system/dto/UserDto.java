package com.wk.entity.system.dto;/**
 * @Author: WANGKANG
 * @Date: 2022/6/2 11:52
 * @Description: 
 */


import com.wk.entity.system.SysDept;
import com.wk.entity.system.SysJob;
import com.wk.entity.system.SysRole;
import com.wk.entity.system.SysUser;
import lombok.Data;

import java.util.List;

/**
 * userDto
 * @author wangkang
 * @date 2022/06/02 11:52
 **/

@Data
public class UserDto extends SysUser {

    private List<SysRole> sysRoles;

    private List<SysJob> sysJobs;

    private SysDept sysDept;

}
