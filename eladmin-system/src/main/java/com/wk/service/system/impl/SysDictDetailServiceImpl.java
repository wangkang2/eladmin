package com.wk.service.system.impl;/**
 * @Author: WANGKANG
 * @Date: 2022/6/8 10:22
 * @Description: 
 */


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wk.base.CacheKey;
import com.wk.entity.system.SysDict;
import com.wk.entity.system.SysDictDetail;
import com.wk.entity.system.qo.DictDetailQuery;
import com.wk.mapper.system.SysDictDetailMapper;
import com.wk.mapper.system.SysDictMapper;
import com.wk.service.system.SysDictDetailService;
import com.wk.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 
 * @author wangkang
 * @date 2022/06/08 10:22
 **/

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "dict")
public class SysDictDetailServiceImpl implements SysDictDetailService {

    private final SysDictDetailMapper sysDictDetailMapper;
    private final SysDictMapper sysDictMapper;
    private final RedisUtils redisUtils;

    @Override
    public Page<SysDictDetail> queryDictDetail(DictDetailQuery dictDetailQuery, Pageable pageable) {
        Page<SysDictDetail> page = new Page<>(pageable.getPageNumber(),pageable.getPageSize());

        QueryWrapper<SysDictDetail> sysDictDetailQueryWrapper = new QueryWrapper<>();

        sysDictDetailQueryWrapper.eq("detail.del_flag","1");

        if(!ObjectUtils.isEmpty(dictDetailQuery.getLabel())){
            sysDictDetailQueryWrapper.like("detail.label",dictDetailQuery.getLabel());
        }

        if(!ObjectUtils.isEmpty(dictDetailQuery.getDictName())){
            sysDictDetailQueryWrapper.eq("dict.name",dictDetailQuery.getDictName());
        }

        sysDictDetailQueryWrapper.orderByAsc("detail.dict_sort");

        Page<SysDictDetail> sysDictDetails = sysDictDetailMapper.queryDictDetail(sysDictDetailQueryWrapper,page);
        return sysDictDetails;
    }

    @Override
    public List<SysDictDetail> findSysDictDetailByName(String name) {

        QueryWrapper<SysDictDetail> sysDictDetailQueryWrapper = new QueryWrapper<>();
        sysDictDetailQueryWrapper.eq("detail.del_flag","1");
        sysDictDetailQueryWrapper.eq("dict.name",name);
        return sysDictDetailMapper.findSysDictDetailByName(sysDictDetailQueryWrapper);
    }

    @Override
    public void create(SysDictDetail sysDictDetail) {
        sysDictDetailMapper.insert(sysDictDetail);
        delCaches(sysDictDetail);
    }

    @Override
    public void update(SysDictDetail sysDictDetail) {
        sysDictDetailMapper.updateById(sysDictDetail);
    }

    public void delCaches(SysDictDetail sysDictDetail){
        SysDict sysDict = sysDictMapper.selectById(sysDictDetail.getDictId());
        redisUtils.del(CacheKey.DICT_NAME + sysDict.getName());
    }

}
