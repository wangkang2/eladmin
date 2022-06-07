package com.wk.service.system;

import com.wk.entity.system.SysDept;
import com.wk.entity.system.dto.DeptDto;
import com.wk.entity.system.qo.DeptQuery;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

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

    List<DeptDto> getSuperior(DeptDto deptDto, ArrayList<DeptDto> deptDtos);

    Map<String, Object> buildTree(List<DeptDto> deptDtos);

    void create(SysDept sysDept);

    void update(SysDept sysDept);

    List<DeptDto> getDeleteDepts(List<DeptDto> deptList, List<DeptDto> deptDtos);

    void delete(List<DeptDto> deptDtos);

    void download(List<DeptDto> queryDept, HttpServletResponse response) throws IOException;
}
