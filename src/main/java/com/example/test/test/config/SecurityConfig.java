/*package com.example.test.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/loginHandler/login", "/loginHandler/index", "/error").permitAll() // Allow login and error pages
                .requestMatchers("/home").authenticated() // Secure home page
                .anyRequest().permitAll() // Allow other endpoints by default
            )
            .formLogin(form -> form
                .loginPage("/loginHandler/login") // Custom login page
                .loginProcessingUrl("/loginHandler/login") // Form POST action
                .defaultSuccessUrl("/home", true) // Redirect after successful login
                .failureUrl("/loginHandler/login?error=true") // Redirect to login page on failure
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout") // Specify logout endpoint
                .logoutSuccessUrl("/loginHandler/login?logout=true") // Redirect after logout
                .permitAll()
            )
            .csrf(csrf -> csrf.disable()); // Disable CSRF for simplicity (not recommended for production)

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // For hashing and validating passwords
    }
}
*/