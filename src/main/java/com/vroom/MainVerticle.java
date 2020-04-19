package com.vroom;

import com.vroom.message.handler.WebSocketTextHandler;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.ServerWebSocket;

public class MainVerticle extends AbstractVerticle {

	@Override
	public void start(Future<Void> startFuture) throws Exception {
		HttpServer server = vertx.createHttpServer();

		server.requestHandler(req -> {
			req.response().putHeader("content-type", "text/plain").end("Hello from Vert.x!");
		});
		
		server.webSocketHandler((ServerWebSocket ws) -> {
			ws.textMessageHandler(new WebSocketTextHandler(ws));
		});
		
		server.listen(8888, http -> {
			if (http.succeeded()) {
				startFuture.complete();
				System.out.println("HTTP server started on port 8888");
			} else {
				startFuture.fail(http.cause());
			}
		});
	}
}
