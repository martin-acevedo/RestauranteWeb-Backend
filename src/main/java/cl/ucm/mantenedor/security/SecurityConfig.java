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
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/categoria/**").hasAnyRole("Mesero", "Administrador")
                        .requestMatchers(HttpMethod.GET, "/plato/**").hasAnyRole("Mesero", "Administrador")
                        .requestMatchers(HttpMethod.GET, "/mesa/**").hasAnyRole("Mesero", "Administrador")
                        .requestMatchers("/categoria/**").hasRole("Administrador")
                        .requestMatchers("/plato/**").hasRole("Administrador")
                        .requestMatchers("/mesa/**").hasRole("Administrador")
                        .requestMatchers(HttpMethod.PUT,"/auth/update-pass/**").hasAnyRole("Administrador", "Mesero")
                        .requestMatchers("/dept/all").hasAnyRole("Mesero","Administrador")
                        .requestMatchers("/dept/save").hasAnyRole("Mesero","Administrador")
                        .requestMatchers(HttpMethod.GET, "/sales/corte/**").hasAnyRole("Mesero","Administrador")
                        .requestMatchers(HttpMethod.GET, "/sales/cortes-salesman/**").hasRole("Administrador")
                        .requestMatchers(HttpMethod.GET, "/sales/dash/**").hasRole("Administrador")
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
