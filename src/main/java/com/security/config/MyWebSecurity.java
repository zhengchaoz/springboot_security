package com.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

/**
 * @author 郑超
 * @create 2021/5/11
 */
// 开启WebSecurity功能，类似的@EnableXXX即开启某功能
@EnableWebSecurity
public class MyWebSecurity extends WebSecurityConfigurerAdapter {

    /**
     * ******** 连接JDBC进行用户权限认证 *********
     * <p>
     * //    @Autowired
     * //    private DataSource dataSource;
     * <p>
     * // 设计认证规则，为不同的用户开启不同的权限认证
     * //    @Override
     * //    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
     * //        User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();
     * //        auth.jdbcAuthentication().dataSource(dataSource).withDefaultSchema().passwordEncoder(new BCryptPasswordEncoder())
     * //                .withUser("zhengC").password(new BCryptPasswordEncoder().encode("123456"))
     * //                .roles("vip2", "vip3")
     * //                .and()
     * //                .withUser("root").password(new BCryptPasswordEncoder().encode("root"))
     * //                .roles("vip1", "vip2", "vip3")
     * //                .and()
     * //                .withUser("guest").password(new BCryptPasswordEncoder().encode("123456"))
     * //                .roles("vip1");
     * //    }
     * <p>
     * ******** 连接JDBC进行用户权限认证 *********
     */

    // 设计请求授权的规则
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 首页所有人都可以访问
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("vip1")
                .antMatchers("/level2/**").hasRole("vip2")
                .antMatchers("/level3/**").hasRole("vip3");
        // 没有权限会默认跳转登录页面
//        http.formLogin();
        // 自定义跳转登录页面
        http.formLogin().loginPage("/toLogin");
        // loginPage：指定实际上的跳转登录的请求路径
        // loginProcessingUrl：指定表面上的跳转登录的请求路径
        // usernameParameter：接受登录页面form表单的参数，默认参数名username
        // passwordParameter：接受登录页面form表单的参数，默认参数名password
//        http.formLogin().loginPage("/toLogin").usernameParameter("user")
//                .passwordParameter("pwd").loginProcessingUrl("/login");

        // 注销，注销成功后跳转指定页面
        http.logout().logoutSuccessUrl("/");

        // 开启记住我功能，cookie默认保存两周
//        http.rememberMe();
        // 定制记住我功能的form表单中的参数
        http.rememberMe().rememberMeParameter("remember");
    }

    // 设计认证规则，为不同的用户开启不同的权限认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("zhengC").password(new BCryptPasswordEncoder().encode("123456"))
                .roles("vip2", "vip3")
                .and()
                .withUser("root").password(new BCryptPasswordEncoder().encode("root"))
                .roles("vip1", "vip2", "vip3")
                .and()
                .withUser("guest").password(new BCryptPasswordEncoder().encode("123456"))
                .roles("vip1");
    }
}
