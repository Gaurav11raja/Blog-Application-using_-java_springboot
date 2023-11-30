package com.springProject.springboot.Main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import javax.sql.DataSource;


@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/","homeStyle.css","/filter","filterStyle.css",
                                        "/viewPost/{id}","viewPostStyle.css","loginStyle.css",
                                        "/showMySignUpPage","/register","/saveComment",
                                "/createUserStyle.css").permitAll()
                                .anyRequest()
                                .authenticated())
                .formLogin(form->
                        form.loginPage("/showMyLoginPage")
                                .loginProcessingUrl("/authenticateTheUser")
                                .permitAll())
                .logout(LogoutConfigurer::permitAll)
                .exceptionHandling(configurer->configurer
                        .accessDeniedPage("/access-denied"));
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery("select username, password,enable from users where username=?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select u.username, r.role from users u " +
                "join roles r on u.username = r.username where u.username=?");
        return  jdbcUserDetailsManager;
    }
}
