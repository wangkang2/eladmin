package com.wk.mapper.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wk.entity.system.SysDictDetail;
import com.wk.entity.system.qo.DictDetailQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @Author: WANGKANG
 * @Date: 2022/6/8 11:13
 * @Description:
 */

@Mapper
public interface SysDictDetailMapper  extends BaseMapper<SysDictDetail> {

    @Select("select detail.detail_id as id,detail.dict_id,detail.label,detail.value,detail.dict_sort from sys_dict_detail detail inner join sys_dict dict on dict.dict_id = detail.dict_id ${ew.customSqlSegment} ")
    Page<SysDictDetail> queryDictDetail(@Param("ew") QueryWrapper<SysDictDetail> queryWrapper, Page page);

    @Select("select detail.detail_id as id,detail.dict_id,detail.label,detail.value,detail.dict_sort from sys_dict_detail detail inner join sys_dict dict on dict.dict_id = detail.dict_id ${ew.customSqlSegment} ")
    List<SysDictDetail> findSysDictDetailByName(@Param("ew") QueryWrapper<SysDictDetail> sysDictDetailQueryWrapper);
}
