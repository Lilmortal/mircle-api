package nz.co.mircle.v1.api.security.model;

/** Created by tanj1 on 24/08/2017. */
public class UserDTO {
  private String emailAddress;

  private String password;

  public UserDTO() {}

  public UserDTO(String emailAddress, String password) {
    this.emailAddress = emailAddress;
    this.password = password;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
