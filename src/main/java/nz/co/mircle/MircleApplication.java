package nz.co.mircle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/** This application is used to create the Mircle CRUD API endpoints. */
@SpringBootApplication
@EnableResourceServer
public class MircleApplication extends SpringBootServletInitializer {
  public static void main(String[] args) {
    SpringApplication.run(MircleApplication.class, args);
  }

  //    @Bean
  //    public BCryptPasswordEncoder bCryptPasswordEncoder() {
  //        return new BCryptPasswordEncoder();
  //    }
  //
  //    public void authenticationManager(AuthenticationManagerBuilder builder, UserRepository userRepository) throws Exception {
  //        builder.userDetailsService(new UserDetailsService() {
  //            @Override
  //            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
  //                return userRepository.findByEmailAddress(username);
  //            }
  //        });
  //    }
}
