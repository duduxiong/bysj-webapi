package com.lzy.mtnj.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description
 * @auther lizhaoyang(李朝阳)
 * @create 2019-10-29 10:09
 */
@tk.mybatis.spring.annotation.MapperScan(basePackages = "com.lzy.mtnj.mapper")
@EnableTransactionManagement
@Configuration
public class MybatisConfig {
}
