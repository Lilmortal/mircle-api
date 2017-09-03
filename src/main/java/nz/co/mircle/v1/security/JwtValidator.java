package nz.co.mircle.v1.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import nz.co.mircle.v1.security.model.JwtUser;
import org.springframework.stereotype.Component;

/**
 * Created by jacktan on 3/09/17.
 */
@Component
public class JwtValidator {

    private static final String SECRET = "secret";

    public JwtUser validate(String token) {
        Claims body = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();

        JwtUser jwtUser = new JwtUser();

        jwtUser.setUsername(body.getSubject());
        jwtUser.setId(Long.parseLong((String)body.get("userId")));
        jwtUser.setRole((String) body.get("role"));

        return jwtUser;
    }
}
