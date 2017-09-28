package nz.co.mircle.v1.api.friend.model;

public class FriendRequest {
  private Long id;

  private Long friendId;

  private String firstName;

  private String surname;

  public FriendRequest(Long id, Long friendId, String firstName, String surname) {
    this.id = id;
    this.friendId = friendId;
    this.firstName = firstName;
    this.surname = surname;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getFriendId() {
    return friendId;
  }

  public void setFriendId(Long friendId) {
    this.friendId = friendId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }
}
