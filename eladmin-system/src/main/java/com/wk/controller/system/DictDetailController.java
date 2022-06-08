/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.wk.controller.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wk.entity.system.SysDictDetail;
import com.wk.entity.system.qo.DictDetailQuery;
import com.wk.exception.BadRequestException;
import com.wk.service.system.SysDictDetailService;
import com.wk.service.system.SysDictService;
import com.wk.utils.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
* @author Zheng Jie
* @date 2019-04-10
*/
@RestController
@RequiredArgsConstructor
@Api(tags = "系统：字典详情管理")
@RequestMapping("/api/dictDetail")
public class DictDetailController {

    private final SysDictDetailService sysDictDetailService;

    @ApiOperation("查询字典详情")
    @GetMapping
    public ResponseEntity<Object> queryDictDetail(DictDetailQuery dictDetailQuery, Pageable pageable){

        Page<SysDictDetail> sysDictDetailPage = sysDictDetailService.queryDictDetail(dictDetailQuery,pageable);

        return new ResponseEntity<>(PageUtil.toPage(sysDictDetailPage.getRecords(),sysDictDetailPage.getTotal()),HttpStatus.OK);
    }

    @ApiOperation("查询多个字典详情")
    @GetMapping(value = "/map")
    public ResponseEntity<Object> getDictDetailMaps(@RequestParam String dictName){
        String[] names = dictName.split("[,，]");
        Map<String, List<SysDictDetail>> dictMap = new HashMap<>(16);
        for (String name : names) {
            dictMap.put(name, sysDictDetailService.findSysDictDetailByName(name));
        }
        return new ResponseEntity<>(dictMap, HttpStatus.OK);
    }

    @ApiOperation("新增字典详情")
    @PostMapping
    @PreAuthorize("@el.check('dict:add')")
    public ResponseEntity<Object> createDictDetail(@RequestBody SysDictDetail sysDictDetail){
        if (sysDictDetail.getId() != null) {
            throw new BadRequestException("A new dictDetail cannot already have an ID");
        }
        sysDictDetailService.create(sysDictDetail);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation("修改字典详情")
    @PutMapping
    @PreAuthorize("@el.check('dict:edit')")
    public ResponseEntity<Object> updateDictDetail(@RequestBody SysDictDetail sysDictDetail){
        sysDictDetailService.update(sysDictDetail);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
//
//    @ApiOperation("删除字典详情")
//    @DeleteMapping(value = "/{id}")
//    @PreAuthorize("@el.check('dict:del')")
//    public ResponseEntity<Object> deleteDictDetail(@PathVariable Long id){
//        dictDetailService.delete(id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}