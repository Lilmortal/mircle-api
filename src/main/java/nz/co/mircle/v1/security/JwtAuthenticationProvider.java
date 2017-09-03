package nz.co.mircle.v1.security;

import nz.co.mircle.v1.security.model.JwtUserDetails;
import nz.co.mircle.v1.security.model.JwtAuthenticationToken;
import nz.co.mircle.v1.security.model.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jacktan on 3/09/17.
 */
@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private JwtValidator jwtValidator;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;

        String token = jwtToken.getToken();

        JwtUser jwtUser = jwtValidator.validate(token);

        if (jwtUser == null) {
            throw new RuntimeException("JWT token is incorrect.");
        }

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(jwtUser.getRole());
        return new JwtUserDetails(jwtUser.getId(), jwtUser.getUsername(), token, grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> authenticationClass) {
        return JwtAuthenticationToken.class.isAssignableFrom(authenticationClass);
    }
}
