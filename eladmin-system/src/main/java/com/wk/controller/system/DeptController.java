package com.wk.controller.system;/**
 * @Author: WANGKANG
 * @Date: 2022/6/1 19:18
 * @Description: 
 */


import cn.hutool.core.collection.CollectionUtil;
import com.wk.entity.system.SysDept;
import com.wk.entity.system.dto.DeptDto;
import com.wk.entity.system.qo.DeptQuery;
import com.wk.exception.BadRequestException;
import com.wk.service.system.SysDeptService;
import com.wk.utils.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 部门
 * @author wangkang
 * @date 2022/06/01 19:18
 **/
@RestController
@Api(tags = "系统：部门管理")
@RequestMapping("/api/dept")
public class DeptController {

    @Autowired
    private SysDeptService sysDeptService;

    @ApiOperation("导出部门数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('dept:list')")
    public void exportDept(HttpServletResponse response, DeptQuery deptQuery) throws Exception {
        sysDeptService.download(sysDeptService.queryDept(deptQuery), response);
    }

    @ApiOperation("查询部门")
    @GetMapping
    @PreAuthorize("@el.check('user:list','dept:list')")
    public ResponseEntity<Object> queryDept(DeptQuery deptQuery) throws Exception {
        List<DeptDto> deptDtos = sysDeptService.queryDept(deptQuery);
        return new ResponseEntity<>(PageUtil.toPage(deptDtos, deptDtos.size()), HttpStatus.OK);
    }

    @ApiOperation("查询部门:根据ID获取同级与上级数据")
    @PostMapping("/superior")
    @PreAuthorize("@el.check('user:list','dept:list')")
    public ResponseEntity<Object> getDeptSuperior(@RequestBody List<Long> ids) {
        Set<DeptDto> deptDtos  = new LinkedHashSet<>();
        for (Long id : ids) {
            DeptDto deptDto = sysDeptService.findDeptDtoById(id);
            List<DeptDto> depts = sysDeptService.getSuperior(deptDto,new ArrayList<>());
            deptDtos.addAll(depts);
        }
        return new ResponseEntity<>(sysDeptService.buildTree(new ArrayList<>(deptDtos)),HttpStatus.OK);
    }

    @ApiOperation("新增部门")
    @PostMapping
    @PreAuthorize("@el.check('dept:add')")
    public ResponseEntity<Object> createDept(@RequestBody SysDept sysDept){
        if (sysDept.getId() != null) {
            throw new BadRequestException("A new dept cannot already have an ID");
        }
        sysDeptService.create(sysDept);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation("修改部门")
    @PutMapping
    @PreAuthorize("@el.check('dept:edit')")
    public ResponseEntity<Object> updateDept(@RequestBody SysDept sysDept){
        sysDeptService.update(sysDept);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation("删除部门")
    @DeleteMapping
    @PreAuthorize("@el.check('dept:del')")
    public ResponseEntity<Object> deleteDept(@RequestBody Set<Long> ids){
        List<DeptDto> deptDtos = new ArrayList<>();
        for (Long id : ids) {
            List<DeptDto> deptList = sysDeptService.findByPid(id);
            deptDtos.add(sysDeptService.findDeptDtoById(id));
            if(CollectionUtil.isNotEmpty(deptList)){
                deptDtos = sysDeptService.getDeleteDepts(deptList, deptDtos);
            }
        }
        // 验证是否被角色或用户关联
        sysDeptService.delete(deptDtos);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
