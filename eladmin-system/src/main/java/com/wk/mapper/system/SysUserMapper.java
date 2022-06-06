package com.wk.mapper.system;/**
 * @Author: WANGKANG
 * @Date: 2022/6/2 16:36
 * @Description: 
 */


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wk.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author wangkang
 * @date 2022/06/02 16:36
 **/

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}
