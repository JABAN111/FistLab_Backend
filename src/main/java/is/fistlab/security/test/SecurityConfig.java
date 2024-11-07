package is.fistlab.security.test;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {


    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer( oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        http.oauth2Login( Customizer.withDefaults());
        return http.authorizeHttpRequests(c -> c.requestMatchers("/api/user/login").permitAll()
                        .requestMatchers("/api/person//create-person").hasRole("SUPER_USER")
                        .anyRequest().authenticated()
                )
                .build();
    }
}