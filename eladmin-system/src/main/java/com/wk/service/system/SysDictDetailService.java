package com.wk.service.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wk.entity.system.SysDictDetail;
import com.wk.entity.system.qo.DictDetailQuery;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author: WANGKANG
 * @Date: 2022/6/8 10:21
 * @Description:
 */
public interface SysDictDetailService {
    Page<SysDictDetail> queryDictDetail(DictDetailQuery dictDetailQuery, Pageable pageable);

    List<SysDictDetail> findSysDictDetailByName(String name);

    void create(SysDictDetail sysDictDetail);

    void update(SysDictDetail sysDictDetail);
}
