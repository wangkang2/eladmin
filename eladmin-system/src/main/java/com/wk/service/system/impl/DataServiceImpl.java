/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.wk.service.system.impl;

import com.wk.entity.system.SysDept;
import com.wk.entity.system.SysRole;
import com.wk.entity.system.dto.UserDto;
import com.wk.enums.DataScopeEnum;
import com.wk.service.system.DataService;
import com.wk.service.system.SysDeptService;
import com.wk.service.system.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Zheng Jie
 * @website https://el-admin.vip
 * @description 数据权限服务实现
 * @date 2020-05-07
 **/
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "data")
public class DataServiceImpl implements DataService {

    private final SysRoleService sysRoleService;
    private final SysDeptService sysDeptService;

    /**
     * 用户角色改变时需清理缓存
     * @param userDto /
     * @return /
     */
    @Override
    @Cacheable(key = "'user:' + #p0.userId")
    public List<Long> getDeptIds(UserDto userDto) {
        // 用于存储部门id
        Set<Long> deptIds = new HashSet<>();
        // 查询用户角色
        List<SysRole> sysRoles = sysRoleService.findSysRoleByUserId(userDto.getUserId());
        // 获取对应的部门ID
        for (SysRole sysRole : sysRoles) {
            DataScopeEnum dataScopeEnum = DataScopeEnum.find(sysRole.getDataScope());
            switch (Objects.requireNonNull(dataScopeEnum)) {
                case THIS_LEVEL:
                    deptIds.add(userDto.getDept().getDeptId());
                    break;
                case CUSTOMIZE:
                    deptIds.addAll(getCustomize(deptIds, sysRole));
                    break;
                default:
                    return new ArrayList<>(deptIds);
            }
        }
        return new ArrayList<>(deptIds);
    }

    /**
     * 获取自定义的数据权限
     * @param deptIds 部门ID
     * @param sysRole 角色
     * @return 数据权限ID
     */
    public Set<Long> getCustomize(Set<Long> deptIds, SysRole sysRole){
        Set<SysDept> sysDepts = sysDeptService.findSysDeptByRoleId(sysRole.getRoleId());
        for (SysDept sysDept : sysDepts) {
            deptIds.add(sysDept.getDeptId());
            List<SysDept> deptChildren = sysDeptService.findByPid(sysDept.getDeptId());
            if (deptChildren != null && deptChildren.size() != 0) {
                deptIds.addAll(sysDeptService.getDeptChildren(deptChildren));
            }
        }
        return deptIds;
    }
}
