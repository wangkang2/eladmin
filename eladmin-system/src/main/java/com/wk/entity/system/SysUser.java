package com.wk.entity.system;/**
 * @Author: WANGKANG
 * @Date: 2022/6/2 11:21
 * @Description: 
 */


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wk.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 用户实体类
 * @author wangkang
 * @date 2022/06/02 11:21
 **/

@Data
@TableName("sys_user")
public class SysUser extends BaseEntity {

    @ApiModelProperty(value = "人员ID", hidden = true)
    @TableId(type = IdType.AUTO,value = "user_id")
    private Long userId;

    @ApiModelProperty(value = "部门ID")
    @TableField("dept_id")
    private Long deptId;

    @ApiModelProperty(value = "用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "昵称")
    @TableField("nick_name")
    private String nickName;

    @ApiModelProperty(value = "性别")
    @TableField("gender")
    private String gender;

    @ApiModelProperty(value = "手机号")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "头像地址")
    @TableField("avatar_name")
    private String avatarName;

    @ApiModelProperty(value = "头像真实地址")
    @TableField("avatar_path")
    private String avatarPath;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "是否为admin账号")
    @TableField("is_admin")
    private Boolean isAdmin;

    @ApiModelProperty(value = "修改密码的时间")
    @TableField("pwd_reset_time")
    private Date pwdResetTime;

}
