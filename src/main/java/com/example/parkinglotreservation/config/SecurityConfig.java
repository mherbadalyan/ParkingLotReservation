package com.example.parkinglotreservation.config;

import com.example.parkinglotreservation.security.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private CustomUserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api/auth/sign-in/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api/auth/sign-up/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT,"/parking-lot-reservation/resident/{phone}/{money}/**").permitAll()
                .antMatchers(HttpMethod.PUT,"/parking-lot-reservation/resident/{phone}/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/parking-lot-reservation/resident/{phone}/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET,"/parking-lot-reservation/resident/**").permitAll()
                .antMatchers("/parking-lot-reservation/parkingPlace/**").hasAuthority("ADMIN")
                .antMatchers("/parking-lot-reservation/book/**").hasAuthority("RESIDENT")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}