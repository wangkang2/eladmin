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
import org.springframework.beans.BeanUtils;
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
    public Set<DeptDto> findSysDeptByRoleId(Long roleId) {
        QueryWrapper<DeptDto> sysDeptQueryWrapper = new QueryWrapper<>();
        sysDeptQueryWrapper.eq("sys_roles_depts.role_id",roleId);
        sysDeptQueryWrapper.eq("sys_role.enabled","1");
        sysDeptQueryWrapper.apply("sys_roles_depts.dept_id = sys_role.dept_id");
        Set<DeptDto> deptDtos = sysDeptMapper.findSysDeptByRoleId(sysDeptQueryWrapper);
        return deptDtos;
    }

    @Override
    public List<DeptDto> findByPid(Long pId) {
        QueryWrapper<SysDept> sysDeptQueryWrapper = new QueryWrapper<>();
        sysDeptQueryWrapper.eq("pid",pId);
        List<SysDept> sysDepts = sysDeptMapper.selectList(sysDeptQueryWrapper);
        List<DeptDto> deptDtos = new ArrayList<>();
        for(SysDept sysDept:sysDepts){
            DeptDto deptDto = new DeptDto();
            BeanUtils.copyProperties(sysDept,deptDto);
            deptDtos.add(deptDto);
        }
        return deptDtos;
    }

    @Override
    public List<Long> getDeptChildren(List<DeptDto> deptList) {
        List<Long> list = new ArrayList<>();
        deptList.forEach(dept -> {
                    if (dept!=null && dept.getEnabled()) {
                        List<DeptDto> depts = findByPid(dept.getId());
                        if (depts.size() != 0) {
                            list.addAll(getDeptChildren(depts));
                        }
                        list.add(dept.getId());
                    }
                }
        );
        return list;
    }

    @Override
    public List<DeptDto> queryDept(DeptQuery deptQuery) {
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

    @Override
    public DeptDto findDeptDtoById(Long id) {

        SysDept sysDept = sysDeptMapper.selectById(id);
        DeptDto deptDto = new DeptDto();
        BeanUtils.copyProperties(sysDept,deptDto);

        return deptDto;
    }

//    @Override
//    public List<DeptDto> getSuperior(DeptDto deptDto, ArrayList<DeptDto> deptDtos) {
//        if(deptDto.getPid() == null){
//            DeptQuery deptQuery = new DeptQuery();
//            deptQuery.setPid(null);
//            List<SysDept> sysDepts = sysDeptMapper.queryDept(deptQuery);
//            for(SysDept sysDept:sysDepts){
//                DeptDto deptDto1 = new DeptDto();
//                BeanUtils.copyProperties(sysDept,deptDto1);
//                deptDto1.
//                deptDtos.add(deptDto1);
//            }
//            return deptDtos;
//        }
//
//        List<SysDept> sysDepts = findByPid(deptDto.getPid());
//
//        deptDtos.addAll());
//        return getSuperior(findById(deptDto.getPid()), depts);
//    }


}
