package com.wk.entity.system;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wk.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;


/**
 * 菜单实体类
 * @author wangkang
 * @date 2022/06/01 18:08
 */
@Data
@TableName("sys_menu")
public class SysMenu extends BaseEntity implements Serializable {


    @ApiModelProperty(value = "菜单ID", hidden = true)
    @TableId(type = IdType.AUTO,value = "menu_id")
    private Long menuId;

    @ApiModelProperty(value = "上级菜单")
    @TableField("pid")
    private Long pid;

    @ApiModelProperty(value = "菜单类型", hidden = true)
    @TableField("sub_count")
    private Integer subCount = 0;

    @ApiModelProperty(value = "子节点数目", hidden = true)
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "菜单标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "菜单名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "组件")
    @TableField("component")
    private String component;

    @ApiModelProperty(value = "排序")
    @TableField("menu_sort")
    private Integer menuSort;

    @ApiModelProperty(value = "图标")
    @TableField("icon")
    private String icon;

    @ApiModelProperty(value = "链接地址")
    @TableField("path")
    private String path;

    @ApiModelProperty(value = "是否外链")
    @TableField("i_frame")
    private Boolean iFrame;

    @ApiModelProperty(value = "缓存")
    @TableField("cache")
    private Boolean cache;

    @ApiModelProperty(value = "隐藏")
    @TableField("hidden")
    private Boolean hidden;

    @ApiModelProperty(value = "权限")
    @TableField("permission")
    private String permission;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SysMenu sysMenu = (SysMenu) o;
        return Objects.equals(menuId, sysMenu.menuId) && Objects.equals(pid, sysMenu.pid) && Objects.equals(subCount, sysMenu.subCount) && Objects.equals(type, sysMenu.type) && Objects.equals(title, sysMenu.title) && Objects.equals(name, sysMenu.name) && Objects.equals(component, sysMenu.component) && Objects.equals(menuSort, sysMenu.menuSort) && Objects.equals(icon, sysMenu.icon) && Objects.equals(path, sysMenu.path) && Objects.equals(iFrame, sysMenu.iFrame) && Objects.equals(cache, sysMenu.cache) && Objects.equals(hidden, sysMenu.hidden) && Objects.equals(permission, sysMenu.permission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), menuId, pid, subCount, type, title, name, component, menuSort, icon, path, iFrame, cache, hidden, permission);
    }
}