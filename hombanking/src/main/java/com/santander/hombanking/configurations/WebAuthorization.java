package com.santander.hombanking.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class WebAuthorization extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/web/js/index.js", "/web/css/**", "/web/index.html", "/web/img/**").permitAll()                    // Permite entrar a todos a la web(Solo al index    )
                .antMatchers(HttpMethod.POST, "/api/clients").permitAll()
                .antMatchers(HttpMethod.POST, "/api/clients/**","/api/cards/**", "/api/loans/**", "/api/accounts/**").hasAnyAuthority("CLIENT", "ADMIN")
                .antMatchers("/api/clients/current/**").hasAnyAuthority("CLIENT", "ADMIN")                                // bloqueda todas las URL /admin, solo entra ADMIN
                .antMatchers("/api/clients/**","/api/cards/**", "/api/loans/**", "/api/accounts/**").hasAuthority("ADMIN")        // bloqueda todas las URL /admin, solo entra ADMIN
                .antMatchers("/**").hasAnyAuthority("CLIENT", "ADMIN");                                                           // bloquea todas las URL /**, solo entran los ADMIN

        http.formLogin()
                .usernameParameter("email")             // este es igual a mi bbdd
                .passwordParameter("password")           // este es igual a mi bbdd
                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout");



         // turn off checking for CSRF tokens
        http.csrf().disable();

        //disabling frameOptions so h2-console can be accessed
        http.headers().frameOptions().disable();

        // if user is not authenticated, just send an authentication failure response
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }


}
