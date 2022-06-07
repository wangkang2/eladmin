package com.wk.service.system.impl;/**
 * @Author: WANGKANG
 * @Date: 2022/6/7 21:58
 * @Description: 
 */


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wk.entity.system.SysDict;
import com.wk.entity.system.SysUser;
import com.wk.entity.system.qo.DictQuery;
import com.wk.mapper.system.SysDictMapper;
import com.wk.service.system.SysDictService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 字典业务实现类
 * @author wangkang
 * @date 2022/06/07 21:58
 **/

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "dict")
public class SysDictServiceImpl implements SysDictService {

    @Autowired
    private SysDictMapper sysDictMapper;

    @Override
    public List<SysDict> queryDict(DictQuery dictQuery) {
        return sysDictMapper.queryDict(dictQuery);
    }

    @Override
    public List<SysDict> queryDict(DictQuery dictQuery, Pageable pageable) {

        Page<SysDict> page = new Page<>(pageable.getPageNumber(),pageable.getPageSize());

        QueryWrapper<SysDict> sysUserQueryWrapper = new QueryWrapper<>();

        if(!ObjectUtils.isEmpty(dictQuery.getName())){
            sysUserQueryWrapper.eq("name",dictQuery.getName());
        }

        sysDictMapper.selectPage(page,sysUserQueryWrapper);
        return page.getRecords();
    }
}
