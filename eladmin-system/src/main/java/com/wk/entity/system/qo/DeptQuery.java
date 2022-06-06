package com.wk.entity.system.qo;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
@Data
public class DeptQuery {

    private String name;

    private Boolean enabled;

    private Long pid;

    private Boolean pidIsNull;

    private List<Timestamp> createTime;
}