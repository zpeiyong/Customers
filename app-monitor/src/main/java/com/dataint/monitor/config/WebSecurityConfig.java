package com.dataint.monitor.config;

import com.dataint.monitor.auth.JwtAuthenticationTokenFilter;
import com.dataint.monitor.auth.handler.MyAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;
    @Autowired
    private MyAccessDeniedHandler deniedHandler;

    /**
     * 配置放行的资源
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/index.html", "/static/**", "/favicon.ico")
                .antMatchers("/user/login", "/user/logout")
                // 给 swagger 放行: 不需要权限能访问的资源
                .antMatchers("/swagger-ui.html", "/swagger-resources/**", "/images/**", "/webjars/**", "/v2/api-docs", "/configuration/ui", "/configuration/security");
    }

    /**
     * http请求设置:
     * - HttpSecurity包含了原数据（主要是url）
     * - 通过withObjectPostProcessor将MyFilterInvocationSecurityMetadataSource和MyAccessDecisionManager注入进来
     * - 此url先被MyFilterInvocationSecurityMetadataSource处理，然后 丢给 MyAccessDecisionManager处理
     * - 如果不匹配，返回 MyAccessDeniedHandler
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 对不同模块加权限
//                .antMatchers("/cf/public/**").hasAnyAuthority("ADMIN", "DBADMIN")
//                .antMatchers("/sys/**").hasAnyAuthority("ADMIN")
                //任何请求都要进行验证
                .anyRequest()
                    .authenticated()
//                // 登录设置(登录页和登录处理)
//                .and()
//                .formLogin()
////                    .loginPage("/login")
//                    .loginProcessingUrl("/user/login")
//                    .usernameParameter("username")
//                    .passwordParameter("password")
//                    .successHandler(new MyAuthenticationSuccessHandler())
//                    .failureHandler(new MyAuthenticationFailureHandler())
//                .permitAll()
//                // 登出设置
//                .and()
//                .logout()
//                    .logoutUrl("/user/logout")
//                    .logoutSuccessHandler(new MyLogoutSuccessHandler())
//                .permitAll()
//                .and()
//                // 在FilterSecurityInterceptor之前添加自定义过滤器
//                .addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class)
//                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                // 添加JWT过滤器
                .and()
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 拒签(403响应)处理器
                .and()
                .exceptionHandling().accessDeniedHandler(deniedHandler)
        ;

        // 关闭csrf和session
        http.csrf().disable();
    }

    /**
     * 自定义获取用户信息接口
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    /**
     * 密码加密算法
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
