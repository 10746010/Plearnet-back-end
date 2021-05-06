package tw.edu.ntub.imd.plearnet.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;

    public SecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
        .authorizeRequests()
        .antMatchers(HttpMethod.POST, "/css/**", "/js/**","/images/**", "/webjars/**", "**/favicon.ico", "/login", "/login1").permitAll()
        .antMatchers("/").permitAll()
        .antMatchers("/course").hasAnyAuthority("ADMIN")
        .antMatchers("/searchCourse").hasAnyAuthority("ADMIN")
        .antMatchers("/courseDetail").hasAnyAuthority("ADMIN")
        .antMatchers("/teacher").hasAnyAuthority("ADMIN")
        .antMatchers("/searchTeacher").hasAnyAuthority("ADMIN")
        .antMatchers("/student").hasAnyAuthority("ADMIN")
        .antMatchers("/searchStudent").hasAnyAuthority("ADMIN")
        .antMatchers("/manageIndex").hasAnyAuthority("ADMIN")
        .antMatchers("/admin").hasAnyAuthority("ADMIN")
//        .anyRequest().authenticated()
        .and()
        .cors()
        .configurationSource(corsConfigurationSource())
        .and()
        .formLogin().loginPage("/login").permitAll()
        .usernameParameter("username")
        .passwordParameter("password")
        .loginProcessingUrl("/login")
        .defaultSuccessUrl("/admin")
        .failureUrl("/login?error=true")
        .and()
        .logout().permitAll()
        .logoutUrl("/logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
        .logoutSuccessUrl("/login?logout")
        .and()
        .csrf().disable();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://211.75.1.204:50001",
                "http://140.131.115.147:3000",
                "http://140.131.115.156:3000",
                "http://140.131.115.162:3000",
                "http://140.131.115.163:3000",
                "http://localhost:3000"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setExposedHeaders(Collections.singletonList("X-Auth-Token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
