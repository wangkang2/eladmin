package com.wk.service.system.impl;/**
 * @Author: WANGKANG
 * @Date: 2022/6/1 19:21
 * @Description: 
 */


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wk.entity.system.SysDept;
import com.wk.entity.system.SysRole;
import com.wk.entity.system.dto.DeptDto;
import com.wk.entity.system.qo.DeptQuery;
import com.wk.enums.DataScopeEnum;
import com.wk.mapper.system.SysDeptMapper;
import com.wk.service.system.SysDeptService;
import com.wk.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * dept
 * @author wangkang
 * @date 2022/06/01 19:21
 **/

@Service
public class SysDeptServiceImpl implements SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Override
    public List<SysDept> queryAll() {
        return sysDeptMapper.selectList(null);
    }

    @Override
    public Set<SysDept> findSysDeptByRoleId(Long roleId) {
        QueryWrapper<SysDept> sysDeptQueryWrapper = new QueryWrapper<>();
        sysDeptQueryWrapper.eq("sys_roles_depts.role_id",roleId);
        sysDeptQueryWrapper.eq("sys_role.enabled","1");
        sysDeptQueryWrapper.apply("sys_roles_depts.dept_id = sys_role.dept_id");
        Set<SysDept> sysDepts = sysDeptMapper.findSysDeptByRoleId(sysDeptQueryWrapper);
        return sysDepts;
    }

    @Override
    public List<SysDept> findByPid(Long pId) {
        QueryWrapper<SysDept> sysDeptQueryWrapper = new QueryWrapper<>();
        sysDeptQueryWrapper.eq("pid",pId);
        return sysDeptMapper.selectList(sysDeptQueryWrapper);
    }

    @Override
    public List<Long> getDeptChildren(List<SysDept> deptList) {
        List<Long> list = new ArrayList<>();
        deptList.forEach(dept -> {
                    if (dept!=null && dept.getEnabled()) {
                        List<SysDept> depts = findByPid(dept.getDeptId());
                        if (depts.size() != 0) {
                            list.addAll(getDeptChildren(depts));
                        }
                        list.add(dept.getDeptId());
                    }
                }
        );
        return list;
    }

    @Override
    public List<SysDept> queryDept(DeptQuery deptQuery) {
        String dataScopeType = SecurityUtils.getDataScopeType();

        if(ObjectUtils.isEmpty(deptQuery.getPid())){
            if(dataScopeType.equals(DataScopeEnum.ALL.getValue())){
                deptQuery.setPidIsNull(true);
            }else{
                List<Long> dataScopes = SecurityUtils.getCurrentUserDataScope();
                deptQuery.setDataScopes(dataScopes);
            }
        }

        return sysDeptMapper.queryDept(deptQuery);
    }


    private List<SysDept> deduplication(List<SysDept> list) {
        List<SysDept> deptDtos = new ArrayList<>();
        for (SysDept deptDto : list) {
            boolean flag = true;
            for (SysDept dto : list) {
                if (dto.getDeptId().equals(deptDto.getPid())) {
                    flag = false;
                    break;
                }
            }
            if (flag){
                deptDtos.add(deptDto);
            }
        }
        return deptDtos;
    }

}
