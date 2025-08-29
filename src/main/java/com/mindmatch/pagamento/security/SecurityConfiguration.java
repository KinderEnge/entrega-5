package com.mindmatch.pagamento.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        PasswordEncoder encoder = passwordEncoder();

        UserDetails manager = User.builder().username("manager").password(encoder.encode("!L3tm3iN!")).roles("MANAGER").build();

        return new InMemoryUserDetailsManager(manager);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                // Allow GET requests (read) without authentication so mobile can list
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(HttpMethod.GET, "/pagamentos/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/pagamentos/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/pagamentos/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/pagamentos/**").hasRole("MANAGER")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
