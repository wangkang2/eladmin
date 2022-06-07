package com.wk.entity.system.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wk.entity.system.SysDept;
import lombok.Data;


import java.util.List;


/**
* @author Zheng Jie
* @date 2019-03-25
*/
@Data
public class DeptDto extends SysDept {

    private Long id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<DeptDto> children;

    public Boolean getHasChildren() {
        return getSubCount() > 0;
    }

    public Boolean getLeaf() {
        return getSubCount() <= 0;
    }

    public String getLabel() {
        return getName();
    }

}