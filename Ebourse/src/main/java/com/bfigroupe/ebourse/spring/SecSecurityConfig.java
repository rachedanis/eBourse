package com.bfigroupe.ebourse.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@ComponentScan(basePackages = { "com.bfigroupe.ebourse.security" })
// @ImportResource({ "classpath:webSecurityConfig.xml" })
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private LogoutSuccessHandler myLogoutSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    public SecSecurityConfig() {
        super();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
        http
        .csrf().disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET,"/users/**").hasAuthority("USERS_UPDATE_PRIVILEGE")
            .antMatchers(HttpMethod.POST,"/users/**").hasAuthority("USERS_UPDATE_PRIVILEGE")
            .antMatchers("/users**").hasAuthority("USERS_UPDATE_PRIVILEGE")
            .antMatchers(HttpMethod.GET,"/backoffice/**").hasAuthority("UPDATE_ORDERS_PRIVILEGE")
            .antMatchers(HttpMethod.POST,"/backoffice/**").hasAuthority("UPDATE_ORDERS_PRIVILEGE")
            .antMatchers("/backoffice**").hasAuthority("UPDATE_ORDERS_PRIVILEGE")
            .antMatchers(HttpMethod.GET,"/frontoffice/**").hasAuthority("ENABLE_USERS_PRIVILEGE")
            .antMatchers(HttpMethod.POST,"/frontoffice/**").hasAuthority("ENABLE_USERS_PRIVILEGE")
            .antMatchers("/frontoffice**").hasAuthority("ENABLE_USERS_PRIVILEGE")
            .antMatchers(HttpMethod.GET,"/home/**").hasAuthority("SERVICE_ACCESS_PRIVILEGE")
            .antMatchers(HttpMethod.POST,"/home/**").hasAuthority("SERVICE_ACCESS_PRIVILEGE")
            .antMatchers("/home**").hasAuthority("SERVICE_ACCESS_PRIVILEGE")
            .antMatchers(HttpMethod.GET,"/orders/**").hasAuthority("SERVICE_ACCESS_PRIVILEGE")
            .antMatchers(HttpMethod.POST,"/orders/**").hasAuthority("SERVICE_ACCESS_PRIVILEGE")
            .antMatchers("/orders**").hasAuthority("SERVICE_ACCESS_PRIVILEGE")
            .antMatchers(HttpMethod.GET,"/executions/**").hasAuthority("SERVICE_ACCESS_PRIVILEGE")
            .antMatchers(HttpMethod.POST,"/executions/**").hasAuthority("SERVICE_ACCESS_PRIVILEGE")
            .antMatchers("/executions**").hasAuthority("SERVICE_ACCESS_PRIVILEGE")
            .antMatchers(HttpMethod.GET,"/bankaccounts/**").hasAuthority("SERVICE_ACCESS_PRIVILEGE")
            .antMatchers(HttpMethod.POST,"/bankaccounts/**").hasAuthority("SERVICE_ACCESS_PRIVILEGE")
            .antMatchers("/bankaccounts**").hasAuthority("SERVICE_ACCESS_PRIVILEGE")
            .antMatchers(HttpMethod.GET,"/marketvalues/**").hasAuthority("SERVICE_ACCESS_PRIVILEGE")
            .antMatchers(HttpMethod.POST,"/marketvalues/**").hasAuthority("SERVICE_ACCESS_PRIVILEGE")
            .antMatchers("/marketvalues**").hasAuthority("SERVICE_ACCESS_PRIVILEGE")
            .antMatchers(HttpMethod.GET,"/requests/**").hasAuthority("SERVICE_ACCESS_PRIVILEGE")
            .antMatchers(HttpMethod.POST,"/requests/**").hasAuthority("SERVICE_ACCESS_PRIVILEGE")
            .antMatchers("/requests**").hasAuthority("SERVICE_ACCESS_PRIVILEGE")
            .antMatchers(HttpMethod.GET,"/portfolios/**").hasAuthority("SERVICE_ACCESS_PRIVILEGE")
            .antMatchers(HttpMethod.POST,"/portfolios/**").hasAuthority("SERVICE_ACCESS_PRIVILEGE")
            .antMatchers("/portfolios**").hasAuthority("SERVICE_ACCESS_PRIVILEGE")
            .antMatchers("/login**","/index**", "/logout**","/user/registration*", "/regitrationConfirm*", "/expiredAccount*",
            			"/registration*","/badUser*", "/user/resendRegistrationToken*" ,"/forgetPassword*",
            			"/user/resetPassword*","/user/changePassword*", "/emailError*", "/resources/**",
            			"/old/user/registration*","/successRegister*","/news*","/contact*","/about*")
            .permitAll()
                .antMatchers("/invalidSession*").anonymous()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home")
                .failureUrl("/login?error=true")
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
            .permitAll()
                .and()
            .sessionManagement()
                .invalidSessionUrl("/invalidSession.html")
                .sessionFixation().none()
            .and()
            .logout()
                .logoutSuccessHandler(myLogoutSuccessHandler)
                .invalidateHttpSession(false)
                .logoutSuccessUrl("/logout.html?logSucc=true")
                .deleteCookies("JSESSIONID")
                .permitAll();
    // @formatter:on
    }

    // beans

    @Bean
    public DaoAuthenticationProvider authProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(11);
	}

}