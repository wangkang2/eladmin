package com.wk.service.system.impl;/**
 * @Author: WANGKANG
 * @Date: 2022/6/7 21:58
 * @Description: 
 */


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wk.base.CacheKey;
import com.wk.entity.system.SysDict;
import com.wk.entity.system.SysUser;
import com.wk.entity.system.qo.DictQuery;
import com.wk.mapper.system.SysDictMapper;
import com.wk.service.system.SysDictService;
import com.wk.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

/**
 * 字典业务实现类
 * @author wangkang
 * @date 2022/06/07 21:58
 **/

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "dict")
public class SysDictServiceImpl implements SysDictService {

    private SysDictMapper sysDictMapper;
    private final RedisUtils redisUtils;

    @Override
    public List<SysDict> queryDict(DictQuery dictQuery) {

        QueryWrapper<SysDict> sysUserQueryWrapper = new QueryWrapper<>();

        if(!ObjectUtils.isEmpty(dictQuery.getName())){
            sysUserQueryWrapper.eq("name",dictQuery.getName());
        }

        return sysDictMapper.selectList(sysUserQueryWrapper);
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

    @Override
    public void create(SysDict sysDict) {
        sysDictMapper.insert(sysDict);
    }

    @Override
    public void update(SysDict sysDict) {
        delCaches(sysDict);
        sysDictMapper.updateById(sysDict);
    }

    @Override
    public void delete(Set<Long> ids) {
        for(Long id:ids){
            SysDict sysDict = sysDictMapper.selectById(id);
            delCaches(sysDict);
            sysDict.setDelFlag(0);
            sysDict.setId(id);
            sysDictMapper.updateById(sysDict);
        }
    }

    @Override
    public void download(List<SysDict> queryDict, HttpServletResponse response) {

    }

    public void delCaches(SysDict sysDict){
        redisUtils.del(CacheKey.DICT_NAME + sysDict.getName());
    }
}
