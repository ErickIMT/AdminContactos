package com.admincontactos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.admincontactos.servicio.ChatWebHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

	private final static String CHAT_ENDPOINT = "/chat";
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(getChatWebHandler(),CHAT_ENDPOINT)
		.setAllowedOrigins("*");
		
	}
	
	@Bean
	public WebSocketHandler getChatWebHandler() {
		return new ChatWebHandler();
	}
	

}
