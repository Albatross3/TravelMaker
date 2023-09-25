package shop.zip.travel.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import shop.zip.travel.global.security.JwsAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public JwsAuthenticationFilter jwsAuthenticationFilter() {
    return new JwsAuthenticationFilter();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .logout().disable()
        .formLogin().disable()
        .httpBasic().disable()
        .authorizeHttpRequests(requests -> requests
            .requestMatchers(HttpMethod.OPTIONS).permitAll()
            .requestMatchers("/api/members/**").permitAll()
            .requestMatchers("/docs/rest-docs.html").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/travelogues/**").permitAll()
            .requestMatchers("/token").permitAll()
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwsAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    return http.build();
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
