package com.wk.service.system;

import com.wk.entity.system.SysDept;
import com.wk.entity.system.dto.DeptDto;
import com.wk.entity.system.qo.DeptQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @Author: WANGKANG
 * @Date: 2022/6/1 19:21
 * @Description:
 */
public interface SysDeptService {

    Set<DeptDto> findSysDeptByRoleId(Long roleId);

    List<DeptDto> findByPid(Long pId);

    List<Long> getDeptChildren(List<DeptDto> deptChildren);

    List<DeptDto> queryDept(DeptQuery deptQuery);

    DeptDto findDeptDtoById(Long id);

//    List<DeptDto> getSuperior(DeptDto deptDto, ArrayList<DeptDto> deptDtos);
}
