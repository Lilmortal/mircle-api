package nz.co.mircle.v1.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Created by jacktan on 3/09/17.
 */
public class JwtUserDetails implements UserDetails {
    private long id;

    private String username;

    private String token;

    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public JwtUserDetails(long id, String username, String token, List<GrantedAuthority> grantedAuthorities) {
        this.id = id;
        this.username = username;
        this.token = token;
        this.grantedAuthorities = grantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Collection<? extends GrantedAuthority> getGrantedAuthorities() {
        return grantedAuthorities;
    }

    public void setGrantedAuthorities(Collection<? extends GrantedAuthority> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
    }
}
