package com.wk.service.system;

import com.wk.entity.system.SysDict;
import com.wk.entity.system.qo.DictQuery;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

/**
 * @Author: WANGKANG
 * @Date: 2022/6/7 21:57
 * @Description:
 */
public interface SysDictService {
    List<SysDict> queryDict(DictQuery dictQuery);

    List<SysDict> queryDict(DictQuery dictQuery, Pageable pageable);

    void create(SysDict sysDict);

    void update(SysDict sysDict);

    void delete(Set<Long> ids);

    void download(List<SysDict> queryDict, HttpServletResponse response);
}
