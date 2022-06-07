package com.wk.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wk.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("sys_dict")
public class SysDict extends BaseEntity {

    @ApiModelProperty(value = "字典ID", hidden = true)
    @TableId(type = IdType.AUTO,value = "dict_id")
    private Long id;

    @ApiModelProperty(value = "字典名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "排序")
    @TableField("dict_sort")
    private Integer dictSort;
}
