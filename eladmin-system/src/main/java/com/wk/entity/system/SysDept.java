package com.wk.entity.system;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.wk.base.BaseEntity;

import java.io.Serializable;
import java.util.Objects;


/**
 * 部门实体类
 * @author wangkang
 * @date 2022/06/01 18:08
 */
@Data
@TableName("sys_dept")
public class SysDept extends BaseEntity implements Serializable {


    @ApiModelProperty(value = "部门ID", hidden = true)
    @TableId(type = IdType.AUTO,value = "dept_id")
    private Long id;

    @ApiModelProperty(value = "上级部门")
    @TableField("pid")
    private Long pid;

    @ApiModelProperty(value = "子节点数目", hidden = true)
    @TableField("sub_count")
    private Integer subCount = 0;

    @ApiModelProperty(value = "部门名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "排序")
    @TableField("dept_sort")
    private Integer deptSort;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        SysDept sysDept = (SysDept) o;
        return Objects.equals(id, sysDept.id) && Objects.equals(pid, sysDept.pid) && Objects.equals(subCount, sysDept.subCount) && Objects.equals(name, sysDept.name) && Objects.equals(deptSort, sysDept.deptSort);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, pid, subCount, name, deptSort);
    }
}