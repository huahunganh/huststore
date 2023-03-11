

package com.huststore.config;

import com.huststore.exceptions.CorsMappingParseException;
import com.huststore.jpa.services.crud.ICustomersCrudService;
import com.huststore.security.IAuthorizationHeaderParserService;
import com.huststore.security.JwtGuestAuthenticationFilter;
import com.huststore.security.JwtLoginAuthenticationFilter;
import com.huststore.security.JwtTokenVerifierFilter;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.crypto.SecretKey;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableAutoConfiguration
public class SecurityConfig
  extends WebSecurityConfigurerAdapter {

  private final UserDetailsService userDetailsService;
  private final SecretKey secretKey;
  private final SecurityProperties securityProperties;
  private final CorsProperties corsProperties;
  private final IAuthorizationHeaderParserService<Claims> jwtClaimsParserService;
  private final ICustomersCrudService customersService;

  @Autowired
  public SecurityConfig(UserDetailsService userDetailsService,
                        SecretKey secretKey,
                        SecurityProperties securityProperties,
                        IAuthorizationHeaderParserService<Claims> jwtClaimsParserService,
                        CorsProperties corsProperties,
                        ICustomersCrudService customersService) {
    this.userDetailsService = userDetailsService;
    this.secretKey = secretKey;
    this.securityProperties = securityProperties;
    this.jwtClaimsParserService = jwtClaimsParserService;
    this.corsProperties = corsProperties;
    this.customersService = customersService;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .headers()
            .frameOptions().sameOrigin()
          .and()
        .cors()
          .and()
        .csrf()
            .disable()
        .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .and()
        .addFilter(
            this.loginFilterForUrl("/public/login"))
        .addFilterAfter(
            this.guestFilterForUrl("/public/guest"),
            JwtLoginAuthenticationFilter.class)
        .addFilterAfter(
            new JwtTokenVerifierFilter(jwtClaimsParserService),
            JwtGuestAuthenticationFilter.class);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(daoAuthenticationProvider());
    if (securityProperties.isGuestUserEnabled() &&
        !securityProperties.getGuestUserName().isBlank()) {
      String credential = securityProperties.getGuestUserName();
      auth.inMemoryAuthentication()
          .withUser(credential)
          .password(passwordEncoder().encode(credential))
          .authorities("checkout");
    }
  }

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(passwordEncoder());
    provider.setUserDetailsService(userDetailsService);
    return provider;
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() throws CorsMappingParseException {
    return new CorsConfigurationSourceBuilder(corsProperties).build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    int strength = securityProperties.getBcryptEncoderStrength();
    return new BCryptPasswordEncoder(strength);
  }

  private UsernamePasswordAuthenticationFilter loginFilterForUrl(String url) throws Exception {
    JwtLoginAuthenticationFilter filter = new JwtLoginAuthenticationFilter(
      securityProperties,
      secretKey,
      super.authenticationManager());
    filter.setFilterProcessesUrl(url);
    return filter;
  }

  private UsernamePasswordAuthenticationFilter guestFilterForUrl(String url) throws Exception {
    JwtGuestAuthenticationFilter filter = new JwtGuestAuthenticationFilter(
      securityProperties,
      secretKey,
      super.authenticationManager(),
      customersService);
    filter.setFilterProcessesUrl(url);
    return filter;
  }

}
