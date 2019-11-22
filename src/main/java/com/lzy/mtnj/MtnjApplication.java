package com.lzy.mtnj;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lzy.mtnj.infrastructure.util.MD5Util;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableTransactionManagement
public class MtnjApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(MtnjApplication.class, args);
        System.out.println("密码(admin)："+MD5Util.getMD5Code("admin"));

    }
    @Bean
    public CacheManager getCacheManager(){
        CacheManager _cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("DefaultCache",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, Object.class, ResourcePoolsBuilder.heap(13)))
                .withCache("login",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, Object.class, ResourcePoolsBuilder.heap(13)).withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(java.time.Duration.ofDays(2))))
                .withCache("tmpCache",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, Object.class, ResourcePoolsBuilder.heap(13)).withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(java.time.Duration.ofMinutes(2))))
                .build(true);
        return _cacheManager;
    }

}
