package com.shavatech.management.infraestructure.websocket;


import io.quarkus.websockets.next.*;
import jakarta.inject.Inject;

import java.util.logging.Logger;

@WebSocketClient(path = "/appointmentfrontweb")
public class JsonWebsocketClient {

    private static final Logger LOG = Logger.getLogger(JsonWebsocketClient.class.getName());

    @Inject
    ReconnectConnectionController reconnectConnectionController;

    @Inject
    DispatchController dispatchController;

    private WebSocketClientConnection connection;

    @OnOpen
    void onOpen(WebSocketClientConnection connection) {
        LOG.info("Conexion iniciada en cliente: " + connection.clientId());
        this.connection = connection;
    }

    @OnClose
    void onClose(WebSocketClientConnection connection) {
        LOG.info("Conexion cerrada en servidor con cliente: " + connection.clientId() + " - se intentara una reconexion");
        reconnectConnectionController.reconnect();
    }

    @OnError
    public void onError(RuntimeException e) {
        e.printStackTrace();
    }

    @OnTextMessage
    void onMessage(BackEndCommand message, WebSocketClientConnection connection) {
        LOG.info("Mensaje recibido: " + message.toString());
        dispatchController.handleMessage(message,connection);
    }
}
