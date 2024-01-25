package kz.abuova.FinalProjectOct.config;


import kz.abuova.FinalProjectOct.services.MyUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public MyUserService userService(){
        return  new MyUserService();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        AuthenticationManagerBuilder authenticationManagerBuilder=
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userService())
                .passwordEncoder(passwordEncoder());
        http.exceptionHandling().accessDeniedPage("/market/403");
        http.formLogin()
                .loginPage("/market/login")
                .loginProcessingUrl("/sign-in")
                .usernameParameter("email-name")
                .passwordParameter("password-name")
                .defaultSuccessUrl("/market/home")
                .failureUrl("/market/login?error");
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/market/login");

        http.csrf().disable();
        return  http.build();
    }
}
