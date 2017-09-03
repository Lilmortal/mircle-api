package nz.co.mircle.v1.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import nz.co.mircle.v1.security.model.JwtUser;
import org.springframework.stereotype.Component;

/**
 * Created by jacktan on 3/09/17.
 */
@Component
public class JwtGenerator {
    public String generate(JwtUser jwtUser) {
        Claims claims = Jwts.claims().setSubject(jwtUser.getUsername());
        claims.put("userId", String.valueOf(jwtUser.getId()));
        claims.put("role", jwtUser.getRole());

        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, "secret").compact();
    }
}
