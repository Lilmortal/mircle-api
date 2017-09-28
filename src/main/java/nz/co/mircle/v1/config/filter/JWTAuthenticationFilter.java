package nz.co.mircle.v1.config.filter;

import static nz.co.mircle.v1.config.security.SecurityConstants.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nz.co.mircle.v1.config.environment.EnvironmentVariablesConfig;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private EnvironmentVariablesConfig environmentVariablesConfig;

  private AuthenticationManager authenticationManager;

  public JWTAuthenticationFilter(
      AuthenticationManager authenticationManager,
      EnvironmentVariablesConfig environmentVariablesConfig) {
    this.environmentVariablesConfig = environmentVariablesConfig;
    this.authenticationManager = authenticationManager;
  }

  // Attempt to authenticate via username and password.
  @Override
  public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
      throws AuthenticationException {
    try {
      nz.co.mircle.v1.api.user.model.User creds =
          new ObjectMapper()
              .readValue(req.getInputStream(), nz.co.mircle.v1.api.user.model.User.class);

      return authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              creds.getUsername(), creds.getPassword(), new ArrayList<>()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  // Creates a token and add it to the response header if successfully authenticated.
  @Override
  protected void successfulAuthentication(
      HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth)
      throws IOException, ServletException {

    String token =
        Jwts.builder()
            .setSubject(((User) auth.getPrincipal()).getUsername())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, environmentVariablesConfig.getJwtSecretKey())
            .compact();
    res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
  }
}
