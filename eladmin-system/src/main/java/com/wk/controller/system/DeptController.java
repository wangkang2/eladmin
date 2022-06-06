package com.wk.controller.system;/**
 * @Author: WANGKANG
 * @Date: 2022/6/1 19:18
 * @Description: 
 */


import com.wk.entity.system.SysDept;
import com.wk.entity.system.qo.DeptQuery;
import com.wk.service.system.SysDeptService;
import com.wk.utils.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
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

    private static final String ENTITY_NAME = "dept";

    @Autowired
    private SysDeptService sysDeptService;

//    @ApiOperation("导出部门数据")
//    @GetMapping(value = "/download")
//    @PreAuthorize("@el.check('dept:list')")
//    public void exportDept(HttpServletResponse response, DeptQueryCriteria criteria) throws Exception {
//        sysDeptService.download(sysDeptService.queryAll(criteria, false), response);
//    }

    @ApiOperation("查询部门")
    @GetMapping
    @PreAuthorize("@el.check('user:list','dept:list')")
    public ResponseEntity<Object> queryDept(DeptQuery deptQuery) throws Exception {
        List<SysDept> sysDepts = sysDeptService.queryDept(deptQuery);
        return new ResponseEntity<>(PageUtil.toPage(sysDepts, sysDepts.size()), HttpStatus.OK);
    }
}
