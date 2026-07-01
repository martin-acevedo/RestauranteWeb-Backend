package cl.ucm.mantenedor.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Autowired
    private CorsConfigurationSource corsConfigurationSource;
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(config -> config.configurationSource(corsConfigurationSource))//.cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/error").permitAll()
                        .requestMatchers(HttpMethod.GET, "/categoria/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/plato/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/mesa/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/categoria/**").hasRole("ADMIN")
                        .requestMatchers("/plato/**").hasRole("ADMIN")
                        .requestMatchers("/mesa/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/auth/update-pass/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/dept/all").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/dept/save").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/sales/corte/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/sales/cortes-salesman/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/sales/dash/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/pedido/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/pedido/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/pedido/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest()
                        .authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
