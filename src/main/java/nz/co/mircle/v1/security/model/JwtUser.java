package nz.co.mircle.v1.security.model;

/**
 * Created by jacktan on 3/09/17.
 */
public class JwtUser {
    private long id;
    private String username;
    private String role;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
