package com.wk.service.system.impl;/**
 * @Author: WANGKANG
 * @Date: 2022/6/7 21:58
 * @Description: 
 */


import com.wk.service.system.SysDictService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * 字典业务实现类
 * @author wangkang
 * @date 2022/06/07 21:58
 **/

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "dict")
public class SysDictServiceImpl implements SysDictService {
    @Override
    public Object queryAllDict() {
        return null;
    }
}
