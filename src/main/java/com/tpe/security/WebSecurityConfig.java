package com.tpe.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configurable
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//!! bu class da amacımız : authenticationManager, Provider ve PasswordEncoder ları olusturup birbirleri ile tanistirmak
    @Autowired
    private UserDetailsService userDetailsService;


    //! http den gelecekleri hiyararşik olarak düzenledik
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().
                authorizeHttpRequests().//!httpden gelen tum hepsini authorized et
                antMatchers("/","index.html","/css/*","/js/*","/register").permitAll().//! bunları security katmannından muaf tut diyoruz
                anyRequest().//! herhangi bir requesti auth et
                authenticated().//! bu işlemi yparken  authenticated kullan
                and().
                httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
     return new BCryptPasswordEncoder(10);
     //* 4-31 e kadar deger alır ne kadar yükselirse hashleme katsayısı artar.
        //* genelde 15 üstüne çok çıkılmaz. 15 den sonrasında encode etme işlemi cok uzun sürüyor.
    }
    @Bean
    public DaoAuthenticationProvider authProvider(){

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }
}
