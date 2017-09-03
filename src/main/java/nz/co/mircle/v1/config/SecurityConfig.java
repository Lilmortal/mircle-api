//package nz.co.mircle.v1.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//
///** Created by tanj1 on 25/08/2017. */
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//  @Bean
//  public UserDetailsService userDetailsService() {
//    return super.userDetailsService();
//  }
//
//  @Bean
//  public BCryptPasswordEncoder passwordEncoder() {
//    return new BCryptPasswordEncoder();
//  }
//
//  @Bean
//  public DaoAuthenticationProvider authProvider() {
//    // This allows us to inject BCrypt password encoder anywhere as BCryptPasswordEncoder creates a new hashing
//    // algorithm everytime it is instantized (can't spell). This creates a singleton that can be used everywhere.
//    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//    authProvider.setUserDetailsService(userDetailsService());
//    authProvider.setPasswordEncoder(passwordEncoder());
//    return authProvider;
//  }
//
//  @Bean
//  public JwtAccessTokenConverter accessTokenConverter() {
//    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//    converter.setSigningKey("123");
//    return converter;
//  }
//
//  @Bean
//  public TokenStore tokenStore() {
//    return new JwtTokenStore(accessTokenConverter());
//  }
//
//  @Bean
//  @Primary
//  public DefaultTokenServices tokenServices() {
//    DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
//    defaultTokenServices.setTokenStore(tokenStore());
//    defaultTokenServices.setSupportRefreshToken(true);
//    return defaultTokenServices;
//  }
//
//  @Override
//  public void configure(WebSecurity web) throws Exception {
//    super.configure(web);
//  }
//
//  @Override
//  public void configure(AuthenticationManagerBuilder auth) throws Exception {
//    auth.authenticationProvider(authProvider());
//  }
//
//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//    http.csrf()
//        .disable()
//        // Because we disable CSRF we need to add this to show h2 page
//        .headers()
//        .frameOptions()
//        .disable()
//        .and()
//        .sessionManagement()
//        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//    //.and().authorizeRequests().antMatchers("/login").permitAll().anyRequest().authenticated();
//  }
//}
