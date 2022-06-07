package com.wk.service.system;

import com.wk.entity.system.SysDept;
import com.wk.entity.system.dto.DeptDto;
import com.wk.entity.system.qo.DeptQuery;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @Author: WANGKANG
 * @Date: 2022/6/1 19:21
 * @Description:
 */
public interface SysDeptService {
    List<SysDept> queryAll();

    Set<SysDept> findSysDeptByRoleId(Long roleId);

    List<SysDept> findByPid(Long pId);

    List<Long> getDeptChildren(List<SysDept> deptChildren);

    List<SysDept> queryDept(DeptQuery deptQuery);
}
