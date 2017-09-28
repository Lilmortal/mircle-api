package nz.co.mircle.v1.api.friend.controller;

import nz.co.mircle.v1.api.friend.model.FriendRequest;
import nz.co.mircle.v1.api.friend.model.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@MessageMapping("/friend")
public class FriendController {
    @MessageMapping()
    @SendTo("/receive/request")
    public Message sendFriendRequest(FriendRequest friendRequest) {
        String message = String.format("%s %s wants to add you, do you accept?", friendRequest.getFirstName(), friendRequest.getSurname());
        Message friendRequestMessage = new Message(friendRequest.getId(), friendRequest.getFriendId(), message);
        return friendRequestMessage;
    }
}
