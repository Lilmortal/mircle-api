package nz.co.mircle.v1.api.friend.model;

public class Message {
  private Long id;

  private Long friendId;

  private String message;

  public Message(Long id, Long friendId, String message) {
    this.id = id;
    this.friendId = friendId;
    this.message = message;
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

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
