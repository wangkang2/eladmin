package com.wk.base;/**
 * @Author: WANGKANG
 * @Date: 2022/6/1 18:24
 * @Description: 
 */


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础类
 * @author wangkang
 * @date 2022/06/01 18:24
 **/

@Data
public class BaseEntity implements Serializable {

    @ApiModelProperty(value = "是否启用")
    @TableField("enabled")
    private Boolean enabled;

    @ApiModelProperty(value = "乐观锁")
    @Version
    private Integer revision;

    @ApiModelProperty(value = "创建人", hidden = true)
    @TableField("create_by")
    private String createBy;

    @ApiModelProperty(value = "更新人", hidden = true)
    @TableField("update_by")
    private String updateBy;

    @ApiModelProperty(value = "创建时间", hidden = true)
    @TableField(fill = FieldFill.INSERT ,value = "create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间", hidden = true)
    @TableField(fill = FieldFill.INSERT_UPDATE ,value = "update_time")
    private LocalDateTime updateTime;
}
