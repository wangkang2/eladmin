package com.wk.service.system;

import com.wk.entity.system.SysDict;
import com.wk.entity.system.qo.DictQuery;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author: WANGKANG
 * @Date: 2022/6/7 21:57
 * @Description:
 */
public interface SysDictService {
    List<SysDict> queryDict(DictQuery dictQuery);

    List<SysDict> queryDict(DictQuery dictQuery, Pageable pageable);
}
