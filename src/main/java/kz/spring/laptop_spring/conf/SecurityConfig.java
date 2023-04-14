package kz.spring.laptop_spring.conf;

import kz.spring.laptop_spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, proxyTargetClass = true)
public class SecurityConfig {

    @Autowired
    private UserService userService;

    @Autowired
    void registerProvider(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.exceptionHandling().accessDeniedPage("/403");

        http.authorizeRequests().antMatchers("/", "/css/**", "/js/**").permitAll();

        http.formLogin()
                .loginPage("/login").permitAll() //login.html
                .usernameParameter("username") //input name="username"
                .passwordParameter("password") //input name="password"
                .loginProcessingUrl("/auth").permitAll() //<form th:action="@{'/auth'}
                .failureUrl("/login?error")
                .defaultSuccessUrl("/");

        http.logout()
                .logoutUrl("/logout").permitAll()
                .logoutSuccessUrl("/login");

        return http.build();
    }
}
