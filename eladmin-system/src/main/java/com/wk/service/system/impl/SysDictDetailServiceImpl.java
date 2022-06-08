package com.wk.service.system.impl;/**
 * @Author: WANGKANG
 * @Date: 2022/6/8 10:22
 * @Description: 
 */


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wk.entity.system.SysDictDetail;
import com.wk.entity.system.qo.DictDetailQuery;
import com.wk.mapper.system.SysDictDetailMapper;
import com.wk.service.system.SysDictDetailService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SysDictDetailServiceImpl implements SysDictDetailService {

    @Autowired
    private SysDictDetailMapper sysDictDetailMapper;

    @Override
    public List<SysDictDetail> queryDictDetail(DictDetailQuery dictDetailQuery, Pageable pageable) {
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
        return page.getRecords();
    }
}
