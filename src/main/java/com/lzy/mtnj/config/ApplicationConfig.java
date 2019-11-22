package com.lzy.mtnj.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class ApplicationConfig extends WebMvcConfigurationSupport {
    /**
     * 验证信息 message
     * @return
     */
    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean(){
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setProviderClass(HibernateValidator.class);
        //ReloadableResourceBundleMessageSource rrbms = new ReloadableResourceBundleMessageSource();
        //rrbms.setBasename("classpath:/lang/messages");
        //rrbms.setUseCodeAsDefaultMessage(false);
        //rrbms.setDefaultEncoding("UTF-8");
        //localValidatorFactoryBean.setValidationMessageSource(rrbms);
        return localValidatorFactoryBean;
    }
    /**
     * 跨域处理 映射所有路径 允许所有来源 以下方法请求
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("*");
    }

}
