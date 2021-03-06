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

    @Select("SELECT sys_dept.dept_id as id,sys_dept.*  FROM sys_dept, sys_roles_depts ${ew.customSqlSegment} ")
    Set<DeptDto> findSysDeptByRoleId(@Param("ew") QueryWrapper<DeptDto> sysDeptQueryWrapper);

    @SelectProvider(type = SysDeptMapper.SysDeptProvider.class,method = "queryDept")
    List<DeptDto> queryDept(DeptQuery deptQuery);

    class SysDeptProvider{
        public String queryDept(DeptQuery deptQuery){
            return new SQL(){{
                SELECT("dept_id as id,pid,sub_count,name,dept_sort,enabled,revision,create_by,create_time");
                FROM("sys_dept");

                WHERE("del_flag = 1 ");

                if(deptQuery.getPidIsNull()){
                    WHERE("pid is null ");
                }

                if(!ObjectUtils.isEmpty(deptQuery.getPid())){
                    WHERE("pid = #{pid} ");
                }

                if(!ObjectUtils.isEmpty(deptQuery.getName())){
                    WHERE("name like concat('%', #{name}, '%')");
                }

                if(!ObjectUtils.isEmpty(deptQuery.getEnabled())){
                    WHERE("enabled = #{enabled} ");
                }

                if(deptQuery.getCreateTime()!=null && deptQuery.getCreateTime().size()==2){
                    WHERE("create_time between '" + deptQuery.getCreateTime().get(0) + "' and '" + deptQuery.getCreateTime().get(1) + "'");
                }

                if(deptQuery.getDataScopes()!=null && deptQuery.getDataScopes().size()>0){
                    StringBuffer str = new StringBuffer();
                    for(Long deptId:deptQuery.getDataScopes()) {
                        str.append(deptId);
                        str.append(",");
                    }
                    WHERE("dept_id in ("+str.substring(0,str.length()-1) + ")");
                }

                ORDER_BY("dept_sort");

            }}.toString();
        }
    }
}
