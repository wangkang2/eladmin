package com.wk.service.system.impl;/**
 * @Author: WANGKANG
 * @Date: 2022/6/1 19:21
 * @Description: 
 */


import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wk.entity.system.SysDept;
import com.wk.entity.system.SysRole;
import com.wk.entity.system.dto.DeptDto;
import com.wk.entity.system.qo.DeptQuery;
import com.wk.enums.DataScopeEnum;
import com.wk.mapper.system.SysDeptMapper;
import com.wk.service.system.SysDeptService;
import com.wk.utils.FileUtil;
import com.wk.utils.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
        sysDeptQueryWrapper.eq("sys_role.del_flag","1");
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

    @Override
    public List<DeptDto> getSuperior(DeptDto deptDto, ArrayList<DeptDto> deptDtos) {
        if(deptDto.getPid() == null){
            DeptQuery deptQuery = new DeptQuery();
            deptQuery.setPid(null);
            List<DeptDto> sysDepts = sysDeptMapper.queryDept(deptQuery);
            deptDtos.addAll(sysDepts);
            return deptDtos;
        }

        deptDtos.addAll(findByPid(deptDto.getPid()));
        return getSuperior(findDeptDtoById(deptDto.getPid()), deptDtos);
    }

    @Override
    public Map<String,Object> buildTree(List<DeptDto> deptDtos) {
        Set<DeptDto> trees = new LinkedHashSet<>();
        Set<DeptDto> depts= new LinkedHashSet<>();
        List<String> deptNames = deptDtos.stream().map(DeptDto::getName).collect(Collectors.toList());
        boolean isChild;
        for (DeptDto deptDTO : deptDtos) {
            isChild = false;
            if (deptDTO.getPid() == null) {
                trees.add(deptDTO);
            }
            for (DeptDto it : deptDtos) {
                if (it.getPid() != null && deptDTO.getId().equals(it.getPid())) {
                    isChild = true;
                    if (deptDTO.getChildren() == null) {
                        deptDTO.setChildren(new ArrayList<>());
                    }
                    deptDTO.getChildren().add(it);
                }
            }
            if(isChild) {
                depts.add(deptDTO);
            } else if(deptDTO.getPid() != null &&  !deptNames.contains(findDeptDtoById(deptDTO.getPid()).getName())) {
                depts.add(deptDTO);
            }
        }

        if (CollectionUtil.isEmpty(trees)) {
            trees = depts;
        }
        Map<String,Object> map = new HashMap<>(2);
        map.put("totalElements",deptDtos.size());
        map.put("content",CollectionUtil.isEmpty(trees)? deptDtos :trees);
        return map;
    }

    @Override
    public void create(SysDept sysDept) {
        sysDeptMapper.insert(sysDept);
    }

    @Override
    public void update(SysDept sysDept) {
        sysDeptMapper.updateById(sysDept);
    }

    @Override
    public List<DeptDto> getDeleteDepts(List<DeptDto> deptList, List<DeptDto> deptDtos) {
        for (DeptDto dept : deptList) {
            deptDtos.add(dept);
            List<DeptDto> depts = findByPid(dept.getId());
            if(depts!=null && depts.size()!=0){
                getDeleteDepts(depts, deptDtos);
            }
        }
        return deptDtos;
    }

    @Override
    public void delete(List<DeptDto> deptDtos) {
        for(DeptDto deptDto:deptDtos){
            SysDept sysDept = new SysDept();
            BeanUtils.copyProperties(deptDto,sysDept);
            sysDept.setDelFlag(0);
            sysDeptMapper.updateById(sysDept);
        }
    }

    @Override
    public void download(List<DeptDto> deptDtos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (DeptDto deptDTO : deptDtos) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("部门名称", deptDTO.getName());
            map.put("部门状态", deptDTO.getEnabled() ? "启用" : "停用");
            map.put("创建日期", deptDTO.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }


}
