package com.wk.config.bean;/**
 * @Author: WANGKANG
 * @Date: 2022/6/7 21:22
 * @Description: 
 */


import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 
 * @author wangkang
 * @date 2022/06/07 21:22
 **/
@EnableTransactionManagement  //开启事务
@Configuration  //配置类注解
public class MybatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());//乐观锁插件拦截器OptimisticLockerInnerInterceptor
        return mybatisPlusInterceptor;
    }
}

