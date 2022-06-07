package com.wk.service.system.impl;/**
 * @Author: WANGKANG
 * @Date: 2022/6/2 16:26
 * @Description: 
 */


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wk.entity.system.SysDept;
import com.wk.entity.system.SysJob;
import com.wk.entity.system.SysRole;
import com.wk.entity.system.SysUser;
import com.wk.entity.system.dto.UserDto;
import com.wk.exception.EntityNotFoundException;
import com.wk.mapper.system.SysDeptMapper;
import com.wk.mapper.system.SysJobMapper;
import com.wk.mapper.system.SysRoleMapper;
import com.wk.mapper.system.SysUserMapper;
import com.wk.service.system.SysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * @author wangkang
 * @date 2022/06/02 16:26
 **/
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysJobMapper sysJobMapper;

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Override
    public UserDto getLoginData(String username) {

        QueryWrapper<SysUser> sysUserQueryWrapper = new QueryWrapper<>();
        sysUserQueryWrapper.eq("username",username);
        sysUserQueryWrapper.eq("enabled","1");

        SysUser sysUser = sysUserMapper.selectOne(sysUserQueryWrapper);

        if (sysUser == null) {
            throw new EntityNotFoundException(SysUser.class, "username", username);
        } else {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(sysUser,userDto);

            QueryWrapper<SysRole> sysRoleQueryWrapper = new QueryWrapper<>();
            sysRoleQueryWrapper.eq("sys_users_roles.user_id",sysUser.getId());
            sysRoleQueryWrapper.eq("sys_role.enabled","1");
            sysRoleQueryWrapper.eq("sys_role.del_flag","1");
            sysRoleQueryWrapper.apply("sys_users_roles.role_id = sys_role.role_id");
            List<SysRole> sysRoleList = sysRoleMapper.findSysRoleByUserId(sysRoleQueryWrapper);
            userDto.setRoles(sysRoleList);

            QueryWrapper<SysJob> sysJobQueryWrapper = new QueryWrapper<>();
            sysJobQueryWrapper.eq("sys_users_jobs.user_id",sysUser.getId());
            sysJobQueryWrapper.eq("sys_job.enabled","1");
            sysJobQueryWrapper.eq("sys_job.del_flag","1");
            sysJobQueryWrapper.apply("sys_users_jobs.job_id = sys_job.job_id");
            List<SysJob> sysJobList = sysJobMapper.findSysJobByUserId(sysJobQueryWrapper);
            userDto.setJobs(sysJobList);

            SysDept sysDept = sysDeptMapper.selectById(sysUser.getDeptId());
            userDto.setDept(sysDept);

            return userDto;

        }

    }
}
