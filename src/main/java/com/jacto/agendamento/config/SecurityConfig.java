package com.jacto.agendamento.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/**").permitAll() // Permite acesso irrestrito a todos os endpoints
                .anyRequest().permitAll() // Certifique-se de que todas as requisições estão permitidas
                .and()
            .csrf().disable() // Desabilita CSRF (útil para APIs)
            .formLogin().disable() // Desabilita o formulário de login padrão
            .httpBasic().disable(); // Desabilita a autenticação básica HTTP
    }
}