package com.vroom.message.handler;

import io.vertx.core.Handler;
import io.vertx.core.http.ServerWebSocket;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebSocketTextHandler implements Handler<String>{

	private ServerWebSocket webSocketRef;
	
	public WebSocketTextHandler(ServerWebSocket ws) {
		this.webSocketRef = ws;
	}

	@Override
	public void handle(String event) {
		log.info("Received request {}", event);
		
	}

}
