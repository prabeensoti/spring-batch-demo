package org.miu.cs590.springbatchdemo.config;

import org.miu.cs590.springbatchdemo.config.jwt.JwtTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig{

    private final JwtTokenFilter jwtTokenFilter;

    public SecurityConfig(JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception {
        return http.cors().and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterAfter(jwtTokenFilter, BasicAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/authenticate").permitAll()
                .antMatchers("/**").authenticated()
                .anyRequest()
                .authenticated().and().build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){
        return new InMemoryUserDetailsManager(
                User.builder().username("admin").password(passwordEncoder.encode("admin123")).roles("ADMIN").build(),
                User.builder().username("user").password(passwordEncoder.encode("user123")).roles("USER").build()
        );
    }
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}