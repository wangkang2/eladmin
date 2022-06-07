package com.wk.entity.system;/**
 * @Author: WANGKANG
 * @Date: 2022/6/2 11:54
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
 * 岗位实体类
 * @author wangkang
 * @date 2022/06/02 11:54
 **/

@Data
@TableName("sys_job")
public class SysJob extends BaseEntity {

    @ApiModelProperty(value = "岗位ID", hidden = true)
    @TableId(type = IdType.AUTO,value = "job_id")
    private Long id;

    @ApiModelProperty(value = "岗位名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "排序")
    @TableField("job_sort")
    private Integer jobSort;
}
