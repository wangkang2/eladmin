package com.wk.mapper.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wk.entity.system.SysJob;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: WANGKANG
 * @Date: 2022/6/2 17:29
 * @Description:
 */

@Mapper
public interface SysJobMapper extends BaseMapper<SysJob> {

    @Select("SELECT sys_job.*  FROM sys_job, sys_users_jobs ${ew.customSqlSegment} ")
    List<SysJob> findSysJobByUserId(@Param("ew") QueryWrapper<SysJob> sysJobQueryWrapper);
}
