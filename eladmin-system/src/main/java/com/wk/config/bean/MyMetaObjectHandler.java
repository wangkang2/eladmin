package com.wk.config.bean;/**
 * @Author: WANGKANG
 * @Date: 2022/6/7 21:21
 * @Description: 
 */


import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.wk.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 
 * @author wangkang
 * @date 2022/06/07 21:21
 **/
@Slf4j //日志
@Component//以组件的形式把这个处理器注册到IOC容器中
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "createBy", String.class, Long.toString(SecurityUtils.getCurrentUserId()));
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, "updateBy", String.class, Long.toString(SecurityUtils.getCurrentUserId()));
    }

    //更新时候启动
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject, "updateBy", String.class, Long.toString(SecurityUtils.getCurrentUserId()));
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }
}

