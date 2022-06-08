package com.wk.entity.system;/**
 * @Author: WANGKANG
 * @Date: 2022/6/8 10:24
 * @Description: 
 */


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 字典详情类
 * @author wangkang
 * @date 2022/06/08 10:24
 **/

@Data
@TableName("sys_dict_detail")
public class SysDictDetail {

    @ApiModelProperty(value = "字典详情ID", hidden = true)
    @TableId(type = IdType.AUTO,value = "detail_id")
    private Long id;

    @ApiModelProperty(value = "字典ID", hidden = true)
    @TableField("dict_id")
    private Long dictId;

    @ApiModelProperty(value = "字典标签")
    @TableField("label")
    private String label;

    @ApiModelProperty(value = "字典值")
    @TableField("value")
    private String value;

    @ApiModelProperty(value = "排序")
    @TableField("dict_sort")
    private Integer dictSort;

}
