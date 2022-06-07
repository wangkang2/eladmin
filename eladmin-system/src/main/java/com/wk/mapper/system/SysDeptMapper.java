package com.wk.mapper.system;/**
 * @Author: WANGKANG
 * @Date: 2022/6/1 19:27
 * @Description: 
 */


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wk.entity.system.SysDept;
import com.wk.entity.system.dto.DeptDto;
import com.wk.entity.system.qo.DeptQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Set;


/**
 * dept mapper
 * @author wangkang
 * @date 2022/06/01 19:27
 **/


@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {

    @Select("SELECT sys_dept.*  FROM sys_dept, sys_roles_depts ${ew.customSqlSegment} ")
    Set<SysDept> findSysDeptByRoleId(@Param("ew") QueryWrapper<SysDept> sysDeptQueryWrapper);

    @SelectProvider(type = SysDeptMapper.SysDeptProvider.class,method = "queryDept")
    List<SysDept> queryDept(DeptQuery deptQuery);

    class SysDeptProvider{
        public String queryDept(DeptQuery deptQuery){
            return new SQL(){{
                SELECT("*");
                FROM("sys_dept");

                if(!ObjectUtils.isEmpty(deptQuery.getPid())){
                    WHERE("pid = #{pid} ");
                }else{
                    WHERE("pid is null ");
                }

                if(!ObjectUtils.isEmpty(deptQuery.getName())){
                    WHERE("name like concat('%', #{name}, '%')");
                }

                if(!ObjectUtils.isEmpty(deptQuery.getEnabled())){
                    WHERE("enabled = #{enabled} ");
                }

                ORDER_BY("dept_sort");

            }}.toString();
        }
    }
}
