package com.lzy.mtnj.config;

import com.lzy.mtnj.infrastructure.shiro.JWTAuthorizingRealm;
import com.lzy.mtnj.infrastructure.shiro.JWTFilter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig implements EnvironmentAware {
    // 这是个DestructionAwareBeanPostProcessor的子类，负责org.apache.shiro.util.Initializable类型bean的生命周期的，
    // 初始化和销毁。主要是AuthorizingRealm类的子类，以及EhCacheManager类
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    // 自定义的认证类，继承自AuthorizingRealm，负责用户的认证和权限的处理
    @Bean(name = "shiroRealm")
    public JWTAuthorizingRealm shiroRealm() {
        JWTAuthorizingRealm realm = new JWTAuthorizingRealm();
        return realm;
    }

    //权限管理
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }

    // 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证 * 配置以下
    // 两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
    @Bean(name = "advisorAutoProxyCreator")
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean(name = "authorizationAttributeSourceAdvisor")
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        shiroFilterFactoryBean.getFilters().put("jwt", new JWTFilter());

        shiroFilterFactoryBean.setSecurityManager(securityManager());
        //shiroFilterFactoryBean.setLoginUrl("/");
        //shiroFilterFactoryBean.setSuccessUrl("/index");
        //shiroFilterFactoryBean.setUnauthorizedUrl("/notlogin");
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/", "user");

        //本地静态文件放权
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");

        //druid监控web页面使用
        filterChainDefinitionMap.put("/druid/css/**", "anon");
        filterChainDefinitionMap.put("/druid/js/**", "anon");
        filterChainDefinitionMap.put("/druid/img/**", "anon");


        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/**.html", "anon");
        filterChainDefinitionMap.put("/user/login", "anon");
        filterChainDefinitionMap.put("/demo/**", "anon");
        filterChainDefinitionMap.put("/verificationCode/*","anon");
        filterChainDefinitionMap.put("/swagger-ui.html","anon");
        filterChainDefinitionMap.put("/export", "anon");
        filterChainDefinitionMap.put("/spiCalc", "anon");
        //filterChainDefinitionMap.put("/hello/**", "anon"); //配置不控制权限请求 anon
        //filterChainDefinitionMap.put("/hellox", "anon");
        filterChainDefinitionMap.put("/druid/*", "anon");
        //filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/export/*", "anon");
        filterChainDefinitionMap.put("/**", "jwt");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
