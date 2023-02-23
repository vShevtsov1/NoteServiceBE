package org.project.NoteService.config;

import org.project.NoteService.user.help.role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtTokenFilter jwtTokenFilter;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http = http.cors().and().csrf().disable();
        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();
        http = http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    ex.getMessage()
                            );
                        }
                )
                .and();
        http.authorizeRequests().mvcMatchers("/users").hasAnyRole(String.valueOf(role.ADMIN))
                .mvcMatchers("/users/delete/**").hasAnyRole(String.valueOf(role.ADMIN))
                .mvcMatchers("/users/update").hasAnyRole(String.valueOf(role.ADMIN))
                .mvcMatchers("/notes/likes").authenticated()
                .mvcMatchers("/users/admin-confirmation").hasAnyRole(String.valueOf(role.ADMIN))
                .anyRequest().permitAll();

        http.addFilterBefore(
                jwtTokenFilter,
                UsernamePasswordAuthenticationFilter.class
        );
    }
}
