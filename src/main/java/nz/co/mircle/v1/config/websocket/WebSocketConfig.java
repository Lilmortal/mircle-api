//package nz.co.mircle.v1.config.websocket;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.config.annotation.*;
//
///*@Configuration
//public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
//  @Override
//  public void configureMessageBroker(MessageBrokerRegistry config) {
//    config.enableSimpleBroker("/pending");
//    config.setApplicationDestinationPrefixes("/friend");
//  }
//
//  @Override
//  public void registerStompEndpoints(StompEndpointRegistry registry) {
//    registry.addEndpoint("/request").setAllowedOrigins("*").withSockJS();
//  }
//}*/
//
//@Configuration
//@EnableWebSocket
//public class WebSocketConfig implements WebSocketConfigurer {
//
//  @Override
//  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//    registry.addHandler(new SocketHandler(), "/name");
//  }
//}