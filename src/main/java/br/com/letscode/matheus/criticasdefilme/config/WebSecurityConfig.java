package br.com.letscode.matheus.criticasdefilme.config;

import br.com.letscode.matheus.criticasdefilme.security.TokenFilterAuthorization;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .addFilterAfter(new TokenFilterAuthorization(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/comment").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers("/movie/rate/**").permitAll()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated();
        http.headers().frameOptions().disable();

    }
}