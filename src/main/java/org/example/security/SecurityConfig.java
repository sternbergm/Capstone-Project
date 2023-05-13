package org.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // This method allows configuring web based security for specific http requests.

    private final JwtConverter converter;

    public SecurityConfig(JwtConverter converter) {
        this.converter = converter;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // we're not using HTML forms in our app
        //so disable CSRF (Cross Site Request Forgery)
        http.csrf().disable();

        // this configures Spring Security to allow
        //CORS related requests (such as preflight checks)
        http.cors();

        // the order of the antMatchers() method calls is important
        // as they're evaluated in the order that they're added
        //todo add endpoints and their permissions:
        //.antMatchers("/authenticate").permitAll()
        //        .antMatchers(HttpMethod.GET, "/order").permitAll()
        //        .antMatchers(HttpMethod.GET, "/sighting", "/sighting/*").permitAll()
        //        .antMatchers(HttpMethod.POST, "/sighting").hasAnyRole("USER", "ADMIN")
        //        .antMatchers(HttpMethod.PUT, "/sighting/*").hasAnyRole("USER", "ADMIN")
        //        .antMatchers(HttpMethod.DELETE, "/sighting/*").hasRole("ADMIN")

        http.authorizeRequests()
                .antMatchers("/authenticate").permitAll()
                .antMatchers("/create_account").permitAll()
                .antMatchers("/refresh_token").authenticated()
                .antMatchers("/**").denyAll()
                // if we get to this point, let's deny all requests
                .and()
                .addFilter(new JwtRequestFilter(authenticationManager(), converter))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {

        // Configure CORS globally versus
        // controller-by-controller.
        // Can be combined with @CrossOrigin.
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("*");
            }
        };
    }
}
