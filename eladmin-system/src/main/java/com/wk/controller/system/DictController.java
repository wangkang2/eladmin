package com.wk.controller.system;/**
 * @Author: WANGKANG
 * @Date: 2022/6/7 21:56
 * @Description: 
 */


import com.wk.entity.system.SysDict;
import com.wk.entity.system.qo.DictQuery;
import com.wk.exception.BadRequestException;
import com.wk.service.system.SysDictService;
import com.wk.utils.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

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

    @ApiOperation("导出字典数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('dict:list')")
    public void exportDict(HttpServletResponse response, DictQuery dictQuery) throws IOException {
        sysDictService.download(sysDictService.queryDict(new DictQuery()), response);
    }

    @ApiOperation("查询字典")
    @GetMapping(value = "/all")
    @PreAuthorize("@el.check('dict:list')")
    public ResponseEntity<List<SysDict>> queryDict(){
        return new ResponseEntity<>(sysDictService.queryDict(new DictQuery()),HttpStatus.OK);
    }

    @ApiOperation("查询字典")
    @GetMapping
    @PreAuthorize("@el.check('dict:list')")
    public ResponseEntity<Object> queryDict(DictQuery dictQuery, Pageable pageable){
        List<SysDict> sysDicts = sysDictService.queryDict(dictQuery,pageable);
        return new ResponseEntity<>(PageUtil.toPage(sysDicts,sysDicts.size()),HttpStatus.OK);
    }

    @ApiOperation("新增字典")
    @PostMapping
    @PreAuthorize("@el.check('dict:add')")
    public ResponseEntity<Object> createDict(@RequestBody SysDict sysDict){
        if (sysDict.getId() != null) {
            throw new BadRequestException("A new dict cannot already have an ID");
        }
        sysDictService.create(sysDict);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation("修改字典")
    @PutMapping
    @PreAuthorize("@el.check('dict:edit')")
    public ResponseEntity<Object> updateDict(@RequestBody SysDict sysDict){
        sysDictService.update(sysDict);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation("删除字典")
    @DeleteMapping
    @PreAuthorize("@el.check('dict:del')")
    public ResponseEntity<Object> deleteDict(@RequestBody Set<Long> ids){
        sysDictService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
