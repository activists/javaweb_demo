package com.fengfw.land.sys_security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Resource
//    private SessionRegistry sessionRegistry; //需要配置监听器

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()  //配置安全策略
                .antMatchers("/").permitAll()  //定义请求不需要验证
                .antMatchers("/modify","/verify","/transfer").hasRole("ADMIN")
                .anyRequest().authenticated()  //其余的所有请求都需要验证
                .and().formLogin().loginPage("/login").permitAll()
//                .defaultSuccessUrl("/userInfo")
                .and().logout().permitAll()   //定义logout不需要验证
                .and().httpBasic()
                .and().csrf().disable();
//        http.sessionManagement().maximumSessions(2).sessionRegistry(sessionRegistry).expiredUrl("/login");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("ffw1").password("123456").roles("ADMIN");
        auth.inMemoryAuthentication().withUser("ffw2").password("234567").roles("ADMIN");
        auth.inMemoryAuthentication().withUser("ffw3").password("345678").roles("USER");
    }
}