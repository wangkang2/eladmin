package com.wk.entity.system;/**
 * @Author: WANGKANG
 * @Date: 2022/6/2 11:59
 * @Description: 
 */


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wk.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色实体类
 * @author wangkang
 * @date 2022/06/02 11:59
 **/

@Data
@TableName("sys_role")
public class SysRole extends BaseEntity {

    @ApiModelProperty(value = "角色ID", hidden = true)
    @TableId(type = IdType.AUTO,value = "role_id")
    private Long roleId;

    @ApiModelProperty(value = "角色名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "角色级别")
    @TableField("level")
    private String level;

    @ApiModelProperty(value = "描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "数据权限")
    @TableField("data_scope")
    private String dataScope;

}
