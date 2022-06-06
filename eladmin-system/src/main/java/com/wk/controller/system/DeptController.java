package com.wk.controller.system;/**
 * @Author: WANGKANG
 * @Date: 2022/6/1 19:18
 * @Description: 
 */


import com.wk.entity.system.SysDept;
import com.wk.service.system.SysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @ApiOperation("查询部门")
    @GetMapping("queryDept")
    public List<SysDept> queryDept() {
        List<SysDept> deptDtos = sysDeptService.queryAll();
        return deptDtos;
    }
}
