package com.wk.controller.system;/**
 * @Author: WANGKANG
 * @Date: 2022/6/7 21:56
 * @Description: 
 */


import com.wk.service.system.SysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 字典控制层
 * @author wangkang
 * @date 2022/06/07 21:56
 **/

@RestController
@RequiredArgsConstructor
@Api(tags = "系统：字典管理")
@RequestMapping("/api/dict")
public class DictController {


    private final SysDictService sysDictService;

//    @ApiOperation("导出字典数据")
//    @GetMapping(value = "/download")
//    @PreAuthorize("@el.check('dict:list')")
//    public void exportDict(HttpServletResponse response, DictQueryCriteria criteria) throws IOException {
//        dictService.download(dictService.queryAll(criteria), response);
//    }

//    @ApiOperation("查询字典")
//    @GetMapping(value = "/all")
//    @PreAuthorize("@el.check('dict:list')")
//    public ResponseEntity<Object> queryDict(){
//        return new ResponseEntity<>(sysDictService.queryAllDict(),HttpStatus.OK);
//    }

}
