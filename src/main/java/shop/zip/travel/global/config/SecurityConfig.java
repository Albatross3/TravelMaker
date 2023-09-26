package shop.zip.travel.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
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
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwsAuthenticationFilter jwsAuthenticationFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .httpBasic().disable()
        .formLogin().disable()
        .csrf().disable()
        .logout().disable()
        .authorizeHttpRequests(requests -> requests
            .requestMatchers(HttpMethod.OPTIONS).permitAll()
            .requestMatchers("/api/members/**").permitAll()
            .requestMatchers("/docs/rest-docs.html").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/travelogues/**").permitAll()
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwsAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    return http.build();
  }

  @Bean
  public FilterRegistrationBean<JwsAuthenticationFilter> filterRegistration() {
    FilterRegistrationBean<JwsAuthenticationFilter> filterRegistrationBean = new FilterRegistrationBean<>();
    filterRegistrationBean.setFilter(jwsAuthenticationFilter);
    filterRegistrationBean.setEnabled(false);
    return filterRegistrationBean;
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
