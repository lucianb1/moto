package ro.motorzz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ro.motorzz.security.TokenAuthenticationEntryPoint;
import ro.motorzz.security.TokenAuthenticationProvider;
import ro.motorzz.security.TokenFilter;
import ro.motorzz.security.WebSocketFilter;
import ro.motorzz.service.api.AuthenticationService;

@Configuration
public class WebSecurityConfig {

    @Order(0)
    public static class WebSecurityConfig0 extends WebSecurityConfigurerAdapter {

        @Autowired
        private TokenAuthenticationProvider tokenAuthenticationProvider;

        @Autowired
        private AuthenticationService authenticationService;

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http
                    .authorizeRequests()
                    .antMatchers(HttpMethod.OPTIONS).permitAll()
                    .anyRequest().authenticated()
                    .and().antMatcher("/greeting/**")
                    .addFilterBefore(tokenFilter(), UsernamePasswordAuthenticationFilter.class)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                    .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint())
                    .and()
                    .csrf().disable()
                    .logout().disable()
                    .httpBasic().disable()
                    .formLogin().disable();
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/forgot-password", "/login", "/registration/**", "/swagger/**", "/v2/api-docs", "/error", "/images/**",
                    "/population", "/contact-us", "/wbsocket/**");
            web.ignoring().antMatchers(HttpMethod.OPTIONS);
        }

        @Override
        protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(this.tokenAuthenticationProvider);
        }

        @Bean
        public AuthenticationEntryPoint authenticationEntryPoint() {
            return new TokenAuthenticationEntryPoint();
        }

        public WebSocketFilter tokenFilter() {
            return new WebSocketFilter(this.tokenAuthenticationProvider, authenticationEntryPoint(), this.authenticationService);
        }
    }


    @Order(1)
    public static class WebSecurityConfig1 extends WebSecurityConfigurerAdapter {

        @Autowired
        private TokenAuthenticationProvider tokenAuthenticationProvider;

        @Autowired
        private AuthenticationService authenticationService;

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http
                    .authorizeRequests()
                    .antMatchers(HttpMethod.OPTIONS).permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .addFilterBefore(tokenFilter(), UsernamePasswordAuthenticationFilter.class)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                    .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint())
                    .and()
                    .csrf().disable()
                    .logout().disable()
                    .httpBasic().disable()
                    .formLogin().disable();
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/forgot-password", "/login", "/registration/**", "/swagger/**", "/v2/api-docs", "/error", "/images/**",
                    "/population", "/contact-us", "/wbsocket/**");
            web.ignoring().antMatchers(HttpMethod.OPTIONS);
        }

        @Override
        protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(this.tokenAuthenticationProvider);
        }

        @Bean
        public AuthenticationEntryPoint authenticationEntryPoint() {
            return new TokenAuthenticationEntryPoint();
        }

        public TokenFilter tokenFilter() {
            return new TokenFilter(this.tokenAuthenticationProvider, authenticationEntryPoint(), this.authenticationService);
        }
    }

}